package cn.mareep.larkbot.listener;

import cn.mareep.larkbot.Bot;
import cn.mareep.larkbot.entity.event.Event;
import cn.mareep.larkbot.event.EventListener;
import cn.mareep.larkbot.event.EventManager;
import cn.mareep.larkbot.entity.event.GroupMessageEvent;
import cn.mareep.larkbot.service.WordpressService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GroupMessageListener implements EventListener {
    private final WordpressService wordpressService = WordpressService.getInstance();
    public GroupMessageListener() {
        EventManager.registerListener(GroupMessageEvent.class, this);
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof GroupMessageEvent groupMessageEvent) {
            String groupId = groupMessageEvent.groupId;
            // Wordpress说说发布
            if (Bot.getConfig().get("wordpressGroupId").equals(groupId)){
                wordpressService.handleGroupMessage(groupMessageEvent);
            }
        }
    }
}
