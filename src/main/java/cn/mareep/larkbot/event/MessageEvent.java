package cn.mareep.larkbot.event;

/**
 * 示例消息事件
 */
public class MessageEvent extends Event {
    public final String message;
    public final String msg_type;
    public MessageEvent(String message, String msg_type) {
        this.message = message;
        this.msg_type = msg_type;
    }

    public String getMessage() {
        return message;
    }
    //TODO:实现获取图片链接
} 