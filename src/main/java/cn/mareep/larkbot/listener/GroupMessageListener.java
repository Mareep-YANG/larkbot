package cn.mareep.larkbot.listener;

import cn.mareep.larkbot.Bot;
import cn.mareep.larkbot.entity.event.Event;
import cn.mareep.larkbot.entity.event.GroupMessageEvent;
import cn.mareep.larkbot.event.EventListener;
import cn.mareep.larkbot.event.EventManager;
import cn.mareep.larkbot.service.WordpressService;

/**
 * 群消息监听器，实现EventListener接口，监听并处理群消息事件.
 */
public class GroupMessageListener implements EventListener {
    private final WordpressService wordpressService = WordpressService.getInstance();

    /**
     * 构造方法，注册群消息事件监听器.
     */
    public GroupMessageListener() {
        EventManager.registerListener(GroupMessageEvent.class, this);
    }

    /**
     * 处理事件回调方法.
     *
     * @param event 事件对象
     */
    @Override
    public void onEvent(Event event) {
        if (event instanceof GroupMessageEvent groupMessageEvent) {
            String groupId = groupMessageEvent.groupId;
            // Wordpress说说发布
            if (Bot.getConfig().get("wordpressGroupId").equals(groupId)) {
                wordpressService.handleGroupMessage(groupMessageEvent);
            }
        }
    }
}
