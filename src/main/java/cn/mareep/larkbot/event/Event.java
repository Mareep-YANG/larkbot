package cn.mareep.larkbot.event;

/**
 * 所有事件的基类
 */
public class Event {
    private boolean cancelled = false;

    /**
     * 事件是否被取消
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * 取消事件
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
} 