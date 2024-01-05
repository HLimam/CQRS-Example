package com.example.cqrs.adapter.in.receources;

import com.example.cqrs.adapter.in.mappers.PersonDtoMapper;
import com.example.cqrs.adapter.in.payload.commands.CreatePersonCommand;
import com.example.cqrs.adapter.in.payload.dto.PersonDTO;
import com.example.cqrs.adapter.in.payload.queries.GetAllPersonsQuery;
import com.example.cqrs.adapter.in.payload.queries.GetPersonByIdQuery;
import com.example.cqrs.domain.models.Person;
import com.example.cqrs.kernel.bus.CommandBus;
import com.example.cqrs.kernel.bus.QueryBus;
import com.example.cqrs.adapter.in.payload.commands.RemovePersonCommand;
import com.example.cqrs.adapter.in.payload.commands.UpdatePersonCommand;
import com.example.cqrs.application.exceptions.PersonNotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(PersonResource.PERSONNE_API_URI)
@Slf4j
@RequiredArgsConstructor
public class PersonResource {
    public static final String PERSONNE_API_URI = "/api/v1/persons";

    private final CommandBus commandBus;
    private final QueryBus queryBus;
    private final PersonDtoMapper mapper;

    /**
     * {@code Post  /persons} : create new Person.
     *
     * @param createPersonCommand the person to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new person.
     */
    @ApiResponse(responseCode = "201", description = "Add Person.")
    @PostMapping
    public ResponseEntity<PersonDTO> register(@Valid @RequestBody CreatePersonCommand createPersonCommand) {
        log.debug("Request to create new person.");
        Person person = commandBus.send(createPersonCommand);
        var location = URI.create(
                ServletUriComponentsBuilder.fromCurrentRequest().build().toUri() + "/" + person.getId()
        );
        return ResponseEntity.created(location).body(mapper.toDto(person));
    }

    /**
     * {@code GET /persons} : get all persons with all the details.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all persons.
     */
    @ApiResponse(responseCode = "200", description = "Get All Personnes.")
    @GetMapping
    public ResponseEntity<List<PersonDTO>> getPersons() {
        log.debug("Request to get all persons.");
        List<Person> persons = queryBus.send(GetAllPersonsQuery.builder().build());
        return ResponseEntity.ok(mapper.toDto(persons));
    }

    /**
     * {@code GET /persons:id} : get the "id" Person.
     *
     * @param id the id of the person to find.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the "id" user.
     * @throws PersonNotFoundException {@code 404 (Not Found)} if the id not found.
     */
    @ApiResponse(responseCode = "200", description = "Get Person by id.")
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Integer id) {
        log.debug("Request to get person by id {id}.", id);
        Person person = queryBus.send(GetPersonByIdQuery.builder().personId(id).build());
        return ResponseEntity.ok(mapper.toDto(person));
    }

    /**
     * {@code PUT /persons} : update an existing Person.
     *
     * @param updatePersonCommand the person to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated user.
     * @throws PersonNotFoundException {@code 404 (Not Found)} if the id not found.
     */
    @ApiResponse(responseCode = "200", description = "Update person.")
    @PutMapping
    public ResponseEntity<PersonDTO> updateUser(@Valid @RequestBody UpdatePersonCommand updatePersonCommand) {
        log.debug("Request to update person : {}", updatePersonCommand.getId());
        Person person = commandBus.send(updatePersonCommand);
        return ResponseEntity.ok(mapper.toDto(person));
    }

    /**
     * {@code DELETE /persons/:id} : delete the "id" Person.
     *
     * @param id the id of the person to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     * @throws PersonNotFoundException {@code 404 (Not Found)} if the id not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        log.debug("Request to delete person: {}", id);
        commandBus.send(RemovePersonCommand.builder().id(id).build());
        return ResponseEntity.noContent().build();
    }
}
