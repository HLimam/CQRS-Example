package com.example.cqrs.application.services.person;

import com.example.cqrs.adapter.in.payload.queries.GetAllPersonsQuery;
import com.example.cqrs.domain.models.Person;
import com.example.cqrs.domain.repositories.PersonRepository;
import com.example.cqrs.kernel.handlers.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GetAllPersonsQueryHandler implements QueryHandler<GetAllPersonsQuery,List<Person>> {
    private final PersonRepository repository;

    @Override
    public List<Person> handle(GetAllPersonsQuery query) {
        return repository.getAll();
    }
}
