package com.example.cqrs.application.services.person;


import com.example.cqrs.application.events.CreatePersonEvent;
import com.example.cqrs.domain.repositories.PersonRepository;
import com.example.cqrs.adapter.in.payload.commands.CreatePersonCommand;
import com.example.cqrs.domain.models.Person;
import com.example.cqrs.kernel.bus.EventBus;
import com.example.cqrs.kernel.handlers.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CreatePersonCommandHandler implements CommandHandler<CreatePersonCommand, Person> {
    private final PersonRepository repository;
    private final EventBus eventBus;

    @Override
    public Person handle(CreatePersonCommand command) {
        var person = Person.builder()
                .lastName(command.getLastName())
                .firstName(command.getFirstName())
                .email(command.getEmail())
                .birthDate(command.getBirthDate())
                .photo(command.getPhoto())
                .build();
        var personDB = repository.create(person);
        eventBus.publish(new CreatePersonEvent(personDB.getId()));
        return  personDB;
    }
}
