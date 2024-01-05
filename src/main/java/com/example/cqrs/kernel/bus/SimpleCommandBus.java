package com.example.cqrs.kernel.bus;

import com.example.cqrs.kernel.Command;
import com.example.cqrs.kernel.handlers.CommandHandler;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class SimpleCommandBus implements CommandBus {

    private final Map<Class<? extends Command>, CommandHandler> dataMap;

    @Override
    public <C extends Command, R> R send(C command) {
        return dispatch(command);
    }

    private <C extends Command, R> R dispatch(C command) {
        final CommandHandler commandHandler = dataMap.get(command.getClass());
        if (commandHandler == null) {
            throw new RuntimeException("No such command handler for " + command.getClass().getName());
        }

        return (R) commandHandler.handle(command);
    }
}
