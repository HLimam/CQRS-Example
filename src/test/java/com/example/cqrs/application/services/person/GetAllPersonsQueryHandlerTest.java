package com.example.cqrs.application.services.person;

import com.example.cqrs.adapter.in.payload.queries.GetAllPersonsQuery;
import com.example.cqrs.domain.models.Person;
import com.example.cqrs.domain.repositories.PersonRepository;
import com.example.cqrs.application.services.person.GetAllPersonsQueryHandler;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class GetAllPersonsQueryHandlerTest {
    @Mock
    private PersonRepository repository;
    @Autowired
    private GetAllPersonsQueryHandler handler;

    @Test
    public void testGetAllPersonsQuery() {
        // Given
        Person person1 = new Person(1,
                "Test",
                "Test",
                "test@example.com",
                LocalDate.of(1990, 1, 1),
                "url-photo");
        Person person2 = new Person(2,
                "Test",
                "Test",
                "test@example.com",
                LocalDate.of(1990, 1, 1),
                "url-photo");
        List<Person> persons = Arrays.asList(person1, person2);

        when(repository.getAll()).thenReturn(persons);

        // When
        List<Person> result = handler.handle( GetAllPersonsQuery.builder().build());

        // Then
        assertEquals(persons, result);
        verify(repository).getAll();
    }
}
