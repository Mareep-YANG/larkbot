package cn.mareep.larkbot.event;

import cn.mareep.larkbot.entity.event.Event;

/**
 * 示例消息事件
 */
public class MessageEvent extends Event {
    public final String message;
    public final String msg_type;
    public final String msg_id;
    public MessageEvent(String message, String msg_type,String msg_id) {
        this.message = message;
        this.msg_type = msg_type;
        this.msg_id = msg_id;
    }

    public String getMessage() {
        return message;
    }
    //TODO:实现获取图片链接
} 