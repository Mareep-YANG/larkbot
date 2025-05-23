package cn.mareep.larkbot.entity.event;

import cn.mareep.larkbot.event.MessageEvent;

/**
 * 群聊消息事件，继承自MessageEvent，包含群ID和用户ID信息.
 */
public class GroupMessageEvent extends MessageEvent {
    /**
     * 群聊ID.
     */
    public String groupId;
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
     * @param groupId  群ID
     * @param userId   用户ID
     */
    public GroupMessageEvent(String message, String msg_type, String msg_id, String groupId, String userId) {
        super(message, msg_type, msg_id);
        this.groupId = groupId;
        this.userId = userId;
    }
}
