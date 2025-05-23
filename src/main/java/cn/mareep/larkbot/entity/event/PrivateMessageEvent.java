package cn.mareep.larkbot.entity.event;

import cn.mareep.larkbot.event.MessageEvent;

public class PrivateMessageEvent extends MessageEvent {
    public String userId;
    public PrivateMessageEvent(String message, String msg_type,String msg_id, String userId) {
        super(message, msg_type,msg_id);
        this.userId = userId;
    }
} 