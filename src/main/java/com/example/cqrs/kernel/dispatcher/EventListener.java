package com.example.cqrs.kernel.dispatcher;

import com.example.cqrs.kernel.Event;

public interface EventListener<E extends Event> {
    void listenTo(E event);
}
