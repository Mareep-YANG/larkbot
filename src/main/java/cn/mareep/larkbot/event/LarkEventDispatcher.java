package cn.mareep.larkbot.event;

import cn.mareep.larkbot.entity.event.GroupMessageEvent;
import cn.mareep.larkbot.entity.event.PrivateMessageEvent;
import com.lark.oapi.event.EventDispatcher;
import com.lark.oapi.service.im.ImService;
import com.lark.oapi.service.im.v1.model.EventMessage;
import com.lark.oapi.service.im.v1.model.EventSender;
import com.lark.oapi.service.im.v1.model.P2MessageReceiveV1;
import com.lark.oapi.service.im.v1.model.P2MessageReceiveV1Data;

/**
 * Lark事件分发器，负责将Lark平台的事件分发到本地事件系统.
 */
public class LarkEventDispatcher {
    /**
     * Lark平台事件处理器.
     */
    private static final EventDispatcher EVENT_HANDLER = EventDispatcher.newBuilder("", "")
            .onP2MessageReceiveV1(new ImService.P2MessageReceiveV1Handler() {
                @Override
                public void handle(P2MessageReceiveV1 event) throws Exception {
                    P2MessageReceiveV1Data eventData = event.getEvent();
                    EventMessage messageEvent = eventData.getMessage();
                    EventSender sender = eventData.getSender();
                    if (messageEvent.getChatType().equals("group")) {
                        GroupMessageEvent groupMessageEvent = new GroupMessageEvent(messageEvent.getContent(), messageEvent.getMessageType(), messageEvent.getMessageId(), messageEvent.getChatId(), sender.getSenderId().getUserId());
                        EventManager.callEvent(groupMessageEvent);
                    } else if (messageEvent.getChatType().equals("p2p")) {
                        PrivateMessageEvent privateMessageEvent = new PrivateMessageEvent(messageEvent.getContent(), messageEvent.getMessageType(), messageEvent.getMessageId(), sender.getSenderId().getUserId());
                        EventManager.callEvent(privateMessageEvent);
                    }
                }
            })
            .build();

    /**
     * 获取事件分发器实例.
     *
     * @return EventDispatcher实例
     */
    public static EventDispatcher getDispatcher() {
        return EVENT_HANDLER;
    }
} 