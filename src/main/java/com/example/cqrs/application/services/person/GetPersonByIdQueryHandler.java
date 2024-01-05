package com.example.cqrs.application.services.person;

import com.example.cqrs.domain.repositories.PersonRepository;
import com.example.cqrs.application.exceptions.PersonNotFoundException;
import com.example.cqrs.adapter.in.payload.queries.GetPersonByIdQuery;
import com.example.cqrs.domain.models.Person;
import com.example.cqrs.kernel.handlers.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class GetPersonByIdQueryHandler implements QueryHandler<GetPersonByIdQuery, Person> {
    private final PersonRepository repository;

    @Override
    public Person handle(GetPersonByIdQuery query) {
        return repository.getById(query.getPersonId())
                .orElseThrow(() -> new PersonNotFoundException(query.getPersonId()));
    }
}
