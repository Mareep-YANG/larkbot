package cn.mareep.larkbot.listener;

import cn.mareep.larkbot.Bot;
import cn.mareep.larkbot.api.MessageApi;
import cn.mareep.larkbot.event.Event;
import cn.mareep.larkbot.event.EventListener;
import cn.mareep.larkbot.event.EventManager;
import cn.mareep.larkbot.event.GroupMessageEvent;
import cn.mareep.larkbot.sessions.MarkdownEditSession;
import cn.mareep.larkbot.utils.WordpressUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class GroupMessageListener implements EventListener {
    // key: 群号，value: 编辑会话
    private final Map<String, MarkdownEditSession> editSessions = new HashMap<>();
    private final String targetGroupId = "oc_5e7a414f2530188dfe51a2e4845cb582";
    private final MessageApi messageApi = new MessageApi(Bot.getConfig().get("larkAppId"), Bot.getConfig().get("larkAppSecret"));

    public GroupMessageListener() {
        EventManager.registerListener(GroupMessageEvent.class, this);
    }

    private void sendMessage(String message) {
        try {
            messageApi.sendTextMessage("chat_id", targetGroupId, message);
        } catch (Exception e) {
            Bot.getLogger().error("发送消息失败", e);
        }
    }

    @Override
    public void onEvent(Event event) {
        GroupMessageEvent messageEvent = (GroupMessageEvent) event;
        String groupId = messageEvent.groupId;
        String message = messageEvent.getMessage();
        JsonObject jsonMessage = JsonParser.parseString(message).getAsJsonObject();
        // 只处理目标群
        if (!targetGroupId.equals(groupId)) return;
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
                        sendMessage("请先设置标题，使用/title <标题>命令");
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
                    }
                }
                else{
                    sendMessage("未配置图片存储方式，图片添加失败");
                }
            }
        }
    }
}
