package com.example.cqrs.application.events;

import com.example.cqrs.kernel.dispatcher.EventListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreatePersonListener1 implements EventListener<CreatePersonEvent> {
    @Override
    public void listenTo(CreatePersonEvent event) {
        log.info("PRIMARY TASK ATTACHED to " + event.getClass().getName());
    }


}
