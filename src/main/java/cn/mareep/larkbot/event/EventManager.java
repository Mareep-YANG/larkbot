package cn.mareep.larkbot.event;

import cn.mareep.larkbot.entity.event.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 事件管理器，负责事件监听器的注册、注销和事件分发.
 */
public class EventManager {
    private static final Map<Class<? extends Event>, List<EventListener<? extends Event>>> listeners = new HashMap<>();

    /**
     * 注册事件监听器.
     */
    public static <T extends Event> void registerListener(Class<T> eventType, EventListener<T> listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    /**
     * 注销事件监听器.
     */
    public static <T extends Event> void unregisterListener(Class<T> eventType, EventListener<T> listener) {
        List<EventListener<? extends Event>> list = listeners.get(eventType);
        if (list != null) {
            list.remove(listener);
        }
    }

    /**
     * 分发事件.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Event> void callEvent(T event) {
        List<EventListener<? extends Event>> list = listeners.get(event.getClass());
        if (list != null) {
            for (EventListener<? extends Event> listener : new ArrayList<>(list)) {
                ((EventListener<T>) listener).onEvent(event);
            }
        }
    }
} 