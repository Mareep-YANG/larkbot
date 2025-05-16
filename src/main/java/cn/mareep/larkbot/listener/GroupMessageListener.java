package cn.mareep.larkbot.listener;

import cn.mareep.larkbot.Bot;
import cn.mareep.larkbot.event.Event;
import cn.mareep.larkbot.event.EventListener;
import cn.mareep.larkbot.event.EventManager;
import cn.mareep.larkbot.event.GroupMessageEvent;
import cn.mareep.larkbot.sessions.MarkdownEditSession;
import cn.mareep.larkbot.utils.WordpressUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

public class GroupMessageListener implements EventListener {
    // key: 群号，value: 编辑会话
    private final Map<String, MarkdownEditSession> editSessions = new HashMap<>();
    private final String targetGroupId = "oc_5e7a414f2530188dfe51a2e4845cb582";

    public GroupMessageListener(){
        EventManager.registerListener(GroupMessageEvent.class,this);
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
                System.out.println("已进入Markdown编辑模式");
                return;
            }

            // 退出并输出HTML
            if (content.equals("/done")) {
                // 必须有编辑会话
                if (!editSessions.containsKey(groupId)) {
                    System.out.println("没有编辑会话，请先输入/edit进入编辑模式");
                    return;
                }
                // 必须设置标题
                if (editSessions.get(groupId).getTitle().isEmpty()) {
                    System.out.println("请先设置标题，使用/title <标题>命令");
                    return;
                }
                MarkdownEditSession session = editSessions.remove(groupId);
                if (session != null) {
                    String html = session.getMarkdownUtil().toHtml();
                    System.out.println("Markdown编辑完成，输出HTML：\n" + html);
                    System.out.println(WordpressUtil.publishPost(Bot.config.get("wordpressUrl"), Bot.config.get("wordpressUsername"), Bot.config.get("wordpressPassword"), session.getTitle(), html, null));
                }
                return;
            }
        }

        // 处理编辑指令
        MarkdownEditSession session = editSessions.get(groupId);
        if (session != null) {
            if (messageEvent.msg_type.equals("text")) {
                String content = jsonMessage.get("text").getAsString();
                if (content.startsWith("/title ")) {
                    String title = content.substring(7);
                    session.setTitle(title);
                    System.out.println("已设置标题: " + title);
                }
                else {
                    // 普通文本视为段落
                    session.getMarkdownUtil().addParagraph(content);
                    System.out.println("已添加段落: " + content);
                }
            }
            if (messageEvent.msg_type.equals("image")) {
                //TODO: 实现添加image
            }
        }
    }
}
