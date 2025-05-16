package cn.mareep.larkbot.listener;

import cn.mareep.larkbot.event.Event;
import cn.mareep.larkbot.event.EventListener;
import cn.mareep.larkbot.event.EventManager;
import cn.mareep.larkbot.event.PrivateMessageEvent;

public class PrivateMessageListener implements EventListener {

    public PrivateMessageListener(){
        EventManager.registerListener(PrivateMessageEvent.class, this);
    }

    @Override
    public void onEvent(Event event) {
        PrivateMessageEvent messageEvent = (PrivateMessageEvent) event;
        System.out.println("收到私聊消息: " + messageEvent.getMessage() + " 发送者id: " + messageEvent.userId + " 消息类型: " + messageEvent.msg_type);
    }
} 