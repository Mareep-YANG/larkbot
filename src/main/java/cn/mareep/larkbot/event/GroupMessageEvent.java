package cn.mareep.larkbot.event;

public class GroupMessageEvent extends MessageEvent{
    public String groupId;
    public String userId;
    public GroupMessageEvent(String message,String msg_type,String msg_id, String groupId, String userId) {
        super(message,msg_type,msg_id);
        this.groupId = groupId;
        this.userId = userId;
    }
}
