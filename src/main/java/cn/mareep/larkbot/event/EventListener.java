package cn.mareep.larkbot.event;

import cn.mareep.larkbot.entity.event.Event;

/**
 * 事件监听器接口，所有监听器需实现.
 */
public interface EventListener<T extends Event> {
    void onEvent(T event);
} 