package com.example.cqrs.adapter.in.payload.commands;

import com.example.cqrs.kernel.Command;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePersonCommand implements Command {
    @NotNull(message = "The person id is required")
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthDate;
    private String photo;

}