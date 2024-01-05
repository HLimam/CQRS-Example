package com.example.cqrs.kernel.bus;

import com.example.cqrs.kernel.Event;
import com.example.cqrs.kernel.dispatcher.EventListener;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class SimpleEventBus<E extends Event> implements EventBus<E> {

    private final Map<Class<? extends E>, List<EventListener<? extends E>>> associatedListeners ;

    @SuppressWarnings("all")
    @Override
    public void publish(E event) {

        var eventListeners = this.associatedListeners.get(event.getClass());
        if (eventListeners == null) {
            //Do nothing
            return;
        }

        for (EventListener eventListener : eventListeners) {
            eventListener.listenTo(event);
        }
    }

    @Override
    public void register(Class<? extends E> classE, List<EventListener<? extends E>> eventListeners) {
        this.associatedListeners.put(classE, eventListeners);
    }
}
