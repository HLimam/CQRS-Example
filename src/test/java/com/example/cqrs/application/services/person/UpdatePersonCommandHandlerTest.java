package com.example.cqrs.application.services.person;

import com.example.cqrs.domain.models.Person;
import com.example.cqrs.domain.repositories.PersonRepository;
import com.example.cqrs.adapter.in.payload.commands.UpdatePersonCommand;
import com.example.cqrs.application.services.person.UpdatePersonCommandHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UpdatePersonCommandHandlerTest {

    @Mock
    private PersonRepository repository;
    @Autowired
    private UpdatePersonCommandHandler handler;
    @Captor
    private ArgumentCaptor<Person> personCaptor;

    @Test
    public void testUpdatePersonCommand() {
        // Given
        var command = new UpdatePersonCommand(101,
                "Test",
                "Test",
                "test@example.com",
                LocalDate.of(1990, 1, 1),
                "url-photo");

        var updatedPerson = new Person(
                101,
                "Test",
                "Test",
                "test@example.com",
                LocalDate.of(1990, 1, 1),
                "url-photo");

        when(repository.update(any())).thenReturn(updatedPerson);

        // When
        var result = handler.handle(command);

        // Then
        verify(repository).create(personCaptor.capture());

        var capturedPerson = personCaptor.getValue();
        assertEquals(command.getFirstName(), capturedPerson.getFirstName());
        assertEquals(command.getLastName(), capturedPerson.getLastName());
        assertEquals(command.getEmail(), capturedPerson.getEmail());
        assertEquals(command.getBirthDate(), capturedPerson.getBirthDate());
        assertEquals(command.getPhoto(), capturedPerson.getPhoto());

        assertEquals(updatedPerson, result);
    }
}
