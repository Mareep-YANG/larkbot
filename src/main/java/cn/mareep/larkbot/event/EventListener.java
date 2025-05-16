package cn.mareep.larkbot.event;

/**
 * 事件监听器接口，所有监听器需实现
 */
public interface EventListener<T extends Event> {
    void onEvent(T event);
} 