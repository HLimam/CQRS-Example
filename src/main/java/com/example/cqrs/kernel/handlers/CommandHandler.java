package com.example.cqrs.kernel.handlers;

import com.example.cqrs.kernel.Command;

@FunctionalInterface
public interface CommandHandler<C extends Command, R> {
    R handle(C command);
}
