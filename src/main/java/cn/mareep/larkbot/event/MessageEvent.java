package cn.mareep.larkbot.event;

import cn.mareep.larkbot.entity.event.Event;

/**
 * 消息事件基类，包含消息内容、类型和ID.
 */
public class MessageEvent extends Event {
    /**
     * 消息内容.
     */
    public final String message;
    /**
     * 消息类型.
     */
    public final String msg_type;
    /**
     * 消息ID.
     */
    public final String msg_id;

    /**
     * 构造方法.
     *
     * @param message  消息内容
     * @param msg_type 消息类型
     * @param msg_id   消息ID
     */
    public MessageEvent(String message, String msg_type, String msg_id) {
        this.message = message;
        this.msg_type = msg_type;
        this.msg_id = msg_id;
    }

    /**
     * 获取消息内容.
     *
     * @return 消息内容
     */
    public String getMessage() {
        return message;
    }
    //TODO:实现获取图片链接
} 