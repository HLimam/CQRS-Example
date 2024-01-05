package com.example.cqrs.infra;


import com.example.cqrs.adapter.in.payload.commands.CreatePersonCommand;
import com.example.cqrs.adapter.in.payload.queries.GetAllPersonsQuery;
import com.example.cqrs.adapter.in.payload.queries.GetPersonByIdQuery;
import com.example.cqrs.application.events.CreatePersonEvent;
import com.example.cqrs.domain.repositories.PersonRepository;
import com.example.cqrs.application.events.CreatePersonListener1;
import com.example.cqrs.application.events.CreatePersonListener2;
import com.example.cqrs.application.services.person.CreatePersonCommandHandler;
import com.example.cqrs.application.services.person.GetAllPersonsQueryHandler;
import com.example.cqrs.application.services.person.GetPersonByIdQueryHandler;
import com.example.cqrs.kernel.Command;
import com.example.cqrs.kernel.Event;
import com.example.cqrs.kernel.Query;
import com.example.cqrs.kernel.bus.CommandBus;
import com.example.cqrs.kernel.bus.EventBus;
import com.example.cqrs.kernel.bus.QueryBus;
import com.example.cqrs.kernel.bus.SimpleCommandBus;
import com.example.cqrs.kernel.bus.SimpleEventBus;
import com.example.cqrs.kernel.bus.SimpleQueryBus;
import com.example.cqrs.kernel.dispatcher.EventListener;
import com.example.cqrs.kernel.handlers.CommandHandler;
import com.example.cqrs.kernel.handlers.QueryHandler;
import com.example.cqrs.adapter.in.payload.commands.RemovePersonCommand;
import com.example.cqrs.application.services.person.RemovePersonCommandHandler;
import com.example.cqrs.adapter.in.payload.commands.UpdatePersonCommand;
import com.example.cqrs.application.services.person.UpdatePersonCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

  //  private final CreatePersonCommandHandler createPersonCommandHandler;
    private final RemovePersonCommandHandler removePersonCommandHandler;
    private final UpdatePersonCommandHandler updatePersonCommandHandler;
    private final GetAllPersonsQueryHandler getAllPersonsQueryHandler;
    private final GetPersonByIdQueryHandler getPersonByIdQueryHandler;
    private final PersonRepository repository;

    @Bean
    public CreatePersonCommandHandler getCreatePersonCommandHandler(){
        return new CreatePersonCommandHandler(repository,getEventBus());
    }

    @Bean
    public CommandBus getCommandBus(){
        final Map<Class<? extends Command>, CommandHandler> commandHandlerMap = new HashMap<>();
        commandHandlerMap.put(CreatePersonCommand.class, getCreatePersonCommandHandler());
        commandHandlerMap.put(UpdatePersonCommand.class, updatePersonCommandHandler);
        commandHandlerMap.put(RemovePersonCommand.class, removePersonCommandHandler);
        return  new SimpleCommandBus(commandHandlerMap);
    }

    @Bean
    public QueryBus getQueryBus(){
        Map<Class<? extends Query>, QueryHandler> queryHandlerMap  = new HashMap<>();
        queryHandlerMap.put(GetAllPersonsQuery.class, getAllPersonsQueryHandler);
        queryHandlerMap.put(GetPersonByIdQuery.class, getPersonByIdQueryHandler);
        return new SimpleQueryBus(queryHandlerMap);
    }

    @Bean
    public EventBus getEventBus() {
        Map<Class<? extends Event>, List<EventListener>> eventListnerMap  = new HashMap<>();
        eventListnerMap.put(CreatePersonEvent.class,List.of(new CreatePersonListener1(),new CreatePersonListener2()));
        return new SimpleEventBus(eventListnerMap);
    }
}
