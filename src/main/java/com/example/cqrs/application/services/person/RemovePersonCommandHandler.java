package com.example.cqrs.application.services.person;

import com.example.cqrs.domain.repositories.PersonRepository;
import com.example.cqrs.kernel.handlers.CommandHandler;
import com.example.cqrs.application.exceptions.PersonNotFoundException;
import com.example.cqrs.adapter.in.payload.commands.RemovePersonCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RemovePersonCommandHandler implements CommandHandler<RemovePersonCommand, Void> {
    private final PersonRepository repository;

    @Override
    public Void handle(RemovePersonCommand command) {
        repository.getById(command.getId()).orElseThrow(() -> new PersonNotFoundException(command.getId()));
        repository.deleteById(command.getId());
        return null;
    }
}
