package com.example.cqrs.adapter.in.receources;

import com.example.cqrs.adapter.in.payload.commands.CreatePersonCommand;
import com.example.cqrs.adapter.in.receources.PersonResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.cqrs.adapter.in.payload.commands.UpdatePersonCommand;
import com.example.cqrs.application.exceptions.PersonNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("/application.yml")
@Sql(value = {"/sql/create-persons.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class PersonResourceTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void getPersonById() throws Exception {
        mockMvc.perform(get(PersonResource.PERSONNE_API_URI+"/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value("Jean"))
                .andExpect(jsonPath("$.lastName").value("Lacoste"))
                .andExpect(jsonPath("$.birthDate").value("1992-02-12"))
                .andExpect(jsonPath("$.email").value("jean@gmail.com"))
                .andExpect(jsonPath("$.photo").isNotEmpty());
    }

    @Test
    public void getPersonResolveNotFoundException() throws Exception {
        mockMvc.perform(get(PersonResource.PERSONNE_API_URI+"/300"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof PersonNotFoundException));

    }

    @Test
    public void getAllPersons() throws Exception {
        mockMvc.perform(get(PersonResource.PERSONNE_API_URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(2)));
    }

    @Test
    public void createPerson() throws Exception {
        CreatePersonCommand createPersonCommand = new CreatePersonCommand("Test", "User", "test@example.com", LocalDate.of(1990,01,01),"url-photo");
        String requestBody = mapper.writeValueAsString(createPersonCommand);

        mockMvc.perform(post(PersonResource.PERSONNE_API_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.lastName").value("User"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.birthDate").value("1990-01-01"))
                .andExpect(jsonPath("$.photo").value("url-photo"));
    }

    @Test
    public void updatePerson() throws Exception {
        UpdatePersonCommand updatePersonCommand = new UpdatePersonCommand(101, "Updated", "User", "updated@example.com", LocalDate.of(1990,01,01),"url-photo");
        String requestBody = mapper.writeValueAsString(updatePersonCommand);

        mockMvc.perform(put(PersonResource.PERSONNE_API_URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value("Updated"))
                .andExpect(jsonPath("$.lastName").value("User"))
                .andExpect(jsonPath("$.email").value("updated@example.com"))
                .andExpect(jsonPath("$.birthDate").value("1990-01-01"))
                .andExpect(jsonPath("$.photo").value("url-photo"));
    }

    @Test
    public void removePerson() throws Exception {
        mockMvc.perform(delete(PersonResource.PERSONNE_API_URI + "/101"))
                .andExpect(status().isNoContent());
    }
}
