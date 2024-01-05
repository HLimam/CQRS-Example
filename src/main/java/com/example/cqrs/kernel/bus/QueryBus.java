package com.example.cqrs.kernel.bus;

import com.example.cqrs.kernel.Query;

public interface QueryBus {
    <Q extends Query, R> R send(Q query);
}
