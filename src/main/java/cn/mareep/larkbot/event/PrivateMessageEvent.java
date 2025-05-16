package cn.mareep.larkbot.event;

public class PrivateMessageEvent extends MessageEvent {
    public String userId;
    public PrivateMessageEvent(String message, String msg_type, String userId) {
        super(message, msg_type);
        this.userId = userId;
    }
} 