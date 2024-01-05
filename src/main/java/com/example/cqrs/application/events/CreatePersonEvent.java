package com.example.cqrs.application.events;


import com.example.cqrs.kernel.Event;

public record CreatePersonEvent(Integer userId) implements Event {

}
