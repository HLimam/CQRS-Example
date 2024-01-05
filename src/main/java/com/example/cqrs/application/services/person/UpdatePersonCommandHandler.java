package com.example.cqrs.application.services.person;


import com.example.cqrs.adapter.in.payload.commands.UpdatePersonCommand;
import com.example.cqrs.domain.models.Person;
import com.example.cqrs.domain.repositories.PersonRepository;
import com.example.cqrs.kernel.handlers.CommandHandler;
import com.example.cqrs.application.exceptions.PersonNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdatePersonCommandHandler implements CommandHandler<UpdatePersonCommand, Person> {
    private final PersonRepository repository;

    @Override
    public Person handle(UpdatePersonCommand command) {
        var person = repository.getById(command.getId()).orElseThrow(() -> new PersonNotFoundException(command.getId()));
        person.setFirstName(command.getFirstName());
        person.setLastName(command.getLastName());
        person.setEmail(command.getEmail());
        person.setBirthDate(command.getBirthDate());
        person.setPhoto(command.getPhoto());
        return repository.update(person);
    }
}
