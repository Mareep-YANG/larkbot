package cn.mareep.larkbot.entity.event;

import cn.mareep.larkbot.event.MessageEvent;

/**
 * 私聊消息事件，继承自MessageEvent，包含用户ID信息.
 */
public class PrivateMessageEvent extends MessageEvent {
    /**
     * 发送者用户ID.
     */
    public String userId;

    /**
     * 构造方法.
     *
     * @param message  消息内容
     * @param msg_type 消息类型
     * @param msg_id   消息ID
     * @param userId   用户ID
     */
    public PrivateMessageEvent(String message, String msg_type, String msg_id, String userId) {
        super(message, msg_type, msg_id);
        this.userId = userId;
    }
} 