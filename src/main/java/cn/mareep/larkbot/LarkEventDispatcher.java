package cn.mareep.larkbot;

import cn.mareep.larkbot.event.*;
import cn.mareep.larkbot.listener.GroupMessageListener;
import cn.mareep.larkbot.listener.PrivateMessageListener;
import com.lark.oapi.event.EventDispatcher;
import com.lark.oapi.service.im.ImService;
import com.lark.oapi.service.im.v1.model.*;

public class LarkEventDispatcher {
    private static final EventDispatcher EVENT_HANDLER = EventDispatcher.newBuilder("", "")
            .onP2MessageReceiveV1(new ImService.P2MessageReceiveV1Handler() {
                @Override
                public void handle(P2MessageReceiveV1 event) throws Exception {
                    P2MessageReceiveV1Data eventData = event.getEvent();
                    EventMessage messageEvent = eventData.getMessage();
                    EventSender sender = eventData.getSender();
                    if (messageEvent.getChatType().equals("group")) {
                        GroupMessageEvent groupMessageEvent = new GroupMessageEvent(messageEvent.getContent(), messageEvent.getMessageType(), messageEvent.getChatId(), sender.getSenderId().getUserId());
                        EventManager.callEvent(groupMessageEvent);
                    } else if (messageEvent.getChatType().equals("p2p")) {
                        PrivateMessageEvent privateMessageEvent = new PrivateMessageEvent(messageEvent.getContent(), messageEvent.getMessageType(), sender.getSenderId().getUserId());
                        EventManager.callEvent(privateMessageEvent);
                    }
                }
            })
            .build();

    public static EventDispatcher getDispatcher() {
        return EVENT_HANDLER;
    }
} 