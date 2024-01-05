package com.example.cqrs.application.services.person;

import com.example.cqrs.adapter.in.payload.commands.CreatePersonCommand;
import com.example.cqrs.domain.models.Person;
import com.example.cqrs.domain.repositories.PersonRepository;
import com.example.cqrs.application.services.person.CreatePersonCommandHandler;
import com.example.cqrs.kernel.bus.EventBus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CreatePersonCommandHandlerTest {
    @Mock
    private PersonRepository repository;
    @Mock
    private EventBus eventBus;
    @InjectMocks
    private CreatePersonCommandHandler handler;
    @Captor
    private ArgumentCaptor<Person> personCaptor;

    @Test
    public void testCreatePersonCommand() {
        // Given
        var command = new CreatePersonCommand(
                "Test",
                "Test",
                "test@example.com",
                LocalDate.of(1990, 1, 1),
                "url-photo");

        var createdPerson = new Person(
                1,
                "Test",
                "Test",
                "test@example.com",
                LocalDate.of(1990, 1, 1),
                "url-photo");

        when(repository.create(any())).thenReturn(createdPerson);

        // When
        var result = handler.handle(command);

        // Then
        verify(repository).create(personCaptor.capture());
        verify(eventBus).publish(any());
        var capturedPerson = personCaptor.getValue();
        assertEquals(command.getFirstName(), capturedPerson.getFirstName());
        assertEquals(command.getLastName(), capturedPerson.getLastName());
        assertEquals(command.getEmail(), capturedPerson.getEmail());
        assertEquals(command.getBirthDate(), capturedPerson.getBirthDate());
        assertEquals(command.getPhoto(), capturedPerson.getPhoto());

        assertEquals(createdPerson, result);
    }
}
