package com.example.cqrs.adapter.in.payload.queries;


import com.example.cqrs.kernel.Query;
import lombok.Builder;

@Builder
public record GetAllPersonsQuery() implements Query {

}