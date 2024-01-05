package com.example.cqrs.kernel.handlers;

import com.example.cqrs.kernel.Query;

@FunctionalInterface
public interface QueryHandler<Q extends Query, R> {
    R handle(Q query);
}