package com.example.cqrs.domain.repositories;


import com.example.cqrs.domain.models.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {
    Person create(Person person);
    List<Person> getAll();
    Optional<Person> getById(Integer id);
    Person update(Person person);
    void deleteById(Integer id);

}
