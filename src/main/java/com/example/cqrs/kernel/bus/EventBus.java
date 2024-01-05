package com.example.cqrs.kernel.bus;

import com.example.cqrs.kernel.Event;
import com.example.cqrs.kernel.dispatcher.EventListener;

import java.util.List;

public interface EventBus<E extends Event> {
    void publish(E event);

    void register(Class<? extends E> classE, List<EventListener<? extends E>> eventListeners);
}
