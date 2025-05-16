package cn.mareep.larkbot.event;

public class GroupMessageEvent extends MessageEvent{
    public String groupId;
    public String userId;
    public GroupMessageEvent(String message,String msg_type, String groupId, String userId) {
        super(message,msg_type);
        this.groupId = groupId;
        this.userId = userId;
    }
}
