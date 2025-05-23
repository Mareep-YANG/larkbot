package cn.mareep.larkbot.service;

import cn.mareep.larkbot.Bot;
import cn.mareep.larkbot.entity.event.GroupMessageEvent;
import cn.mareep.larkbot.entity.session.MarkdownEditSession;
import cn.mareep.larkbot.utils.WordpressUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Wordpress服务类，负责处理群组消息并与Wordpress进行交互.
 * 支持Markdown编辑、图片上传、文章发布等功能。
 * 单例模式实现。
 */
public class WordpressService implements IService {
    private static volatile WordpressService instance;

    private WordpressService() {
    }

    /**
     * 获取WordpressService单例实例.
     *
     * @return WordpressService实例
     */
    public static WordpressService getInstance() {
        if (instance == null) {
            synchronized (WordpressService.class) {
                if (instance == null) {
                    instance = new WordpressService();
                }
            }
        }
        return instance;
    }

    private final Map<String, MarkdownEditSession> editSessions = new HashMap<>();
    private final MessageService messageApi = new MessageService(Bot.getConfig().get("larkAppId"), Bot.getConfig().get("larkAppSecret"));

    /**
     * 处理群组消息，根据消息内容进行Markdown编辑、图片上传、文章发布等操作.
     *
     * @param messageEvent 群组消息事件
     */
    public void handleGroupMessage(GroupMessageEvent messageEvent) {
        String groupId = messageEvent.groupId;
        JsonObject jsonMessage = JsonParser.parseString(messageEvent.getMessage()).getAsJsonObject();
        if (messageEvent.msg_type.equals("text")) {
            String content = jsonMessage.get("text").getAsString();
            // 进入编辑模式
            if (content.equals("/edit")) {
                editSessions.put(groupId, new MarkdownEditSession());
                sendMessage("已进入Markdown编辑模式");
                return;
            }
        }

        // 处理编辑指令
        MarkdownEditSession session = editSessions.get(groupId);
        if (session != null) {
            if (messageEvent.msg_type.equals("text")) {
                String content = jsonMessage.get("text").getAsString();
                // 退出编辑模式
                if (content.equals("/exit")) {
                    editSessions.remove(groupId);
                    sendMessage("已退出Markdown编辑模式");
                    return;
                }
                // 退出并输出HTML
                if (content.equals("/done")) {
                    // 必须设置标题
                    if (editSessions.get(groupId).getTitle().isEmpty()) {
                        sendMessage("WARN！请先设置标题，使用/title <标题>命令");
                        return;
                    }
                    session = editSessions.remove(groupId);
                    if (session != null) {
                        String html = session.getMarkdownUtil().toHtml();
                        sendMessage(WordpressUtil.publishPost(Bot.getConfig().get("wordpressUrl"), Bot.getConfig().get("wordpressUsername"), Bot.getConfig().get("wordpressPassword"), session.getTitle(), html, null));
                    }
                    return;
                }
                // 设置标题
                if (content.startsWith("/title ")) {
                    String title = content.substring(7);
                    session.setTitle(title);
                    sendMessage("已设置标题: " + title);
                }
                // 添加段落
                else {
                    // 普通文本视为段落
                    session.getMarkdownUtil().addParagraph(content);
                    sendMessage("已添加段落: " + content);
                }
            }
            if (messageEvent.msg_type.equals("image")) {
                if (Bot.getConfig().get("imageType").equals("TencentCOS")) {
                    String imageKey = jsonMessage.get("image_key").getAsString();
                    try {
                        String fileName = "shuoshuo_" + Arrays.hashCode(messageApi.getMessageImage(messageEvent.msg_id, imageKey)) + ".jpg";
                        session.getCosUtil().uploadImage(fileName, messageApi.getMessageImage(messageEvent.msg_id, imageKey));
                        session.getMarkdownUtil().addImage("", Bot.getConfig().get("imageBaseUrl") + fileName);
                        sendMessage("已上传图片");
                    } catch (Exception e) {
                        Bot.getLogger().error("消息图片获取失败");
                        sendMessage("ERROR！图片接收或上传失败，请检查配置");
                    }
                } else {
                    sendMessage("ERROR！未配置图片存储方式，图片添加失败");
                }
            }
        }
    }

    private void sendMessage(String message) {
        try {
            messageApi.sendTextMessage("chat_id", Bot.getConfig().get("wordpressGroupId"), message);
        } catch (Exception e) {
            Bot.getLogger().error("发送消息失败", e);
        }
    }
}

