package com.example.cqrs.kernel.bus;

import com.example.cqrs.kernel.Command;

public interface CommandBus {
    <C extends Command, R> R send(C command);
}

