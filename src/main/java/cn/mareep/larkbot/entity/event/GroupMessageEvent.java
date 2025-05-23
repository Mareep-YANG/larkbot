package cn.mareep.larkbot.entity.event;

import cn.mareep.larkbot.event.MessageEvent;

public class GroupMessageEvent extends MessageEvent {
    public String groupId;
    public String userId;
    public GroupMessageEvent(String message,String msg_type,String msg_id, String groupId, String userId) {
        super(message,msg_type,msg_id);
        this.groupId = groupId;
        this.userId = userId;
    }
}
