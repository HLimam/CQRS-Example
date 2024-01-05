package com.example.cqrs.application.services.person;

import com.example.cqrs.domain.models.Person;
import com.example.cqrs.domain.repositories.PersonRepository;
import com.example.cqrs.adapter.in.payload.commands.RemovePersonCommand;
import com.example.cqrs.application.services.person.RemovePersonCommandHandler;
import com.example.cqrs.application.exceptions.PersonNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RemovePersonCommandHandlerTest {
    @Mock
    private PersonRepository repository;

    @Autowired
    private RemovePersonCommandHandler handler;

    @Test
    public void testRemovePersonCommand() {
        // Given
        var id = 1;
        var command = new RemovePersonCommand(id);
        when(repository.getById(anyInt())).thenReturn(Optional.of(new Person()));
        doNothing().when(repository).deleteById(id);

        // When
        assertDoesNotThrow(() -> handler.handle(command));
        // Then
        verify(repository).deleteById(id);
        verify(repository).getById(id);
    }

    @Test
    public void testRemovePersonCommandWhenPersonNotFound() {
        // Given
        var id = 1;
        var command = new RemovePersonCommand(id);

        when(repository.getById(anyInt())).thenReturn(Optional.empty());
        doNothing().when(repository).deleteById(anyInt());

        // When
        PersonNotFoundException exception = assertThrows(
                PersonNotFoundException.class,
                () -> handler.handle(command));

        // Then
        assertEquals("Person with ID " + id + " not found.", exception.getMessage());
        verify(repository).getById(id);
        verify(repository, never()).deleteById(anyInt());
    }

}
