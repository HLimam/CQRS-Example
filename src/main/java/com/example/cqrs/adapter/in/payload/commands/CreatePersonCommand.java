package com.example.cqrs.adapter.in.payload.commands;

import com.example.cqrs.kernel.Command;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class CreatePersonCommand implements Command {
    @NotNull
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotNull
    @NotBlank(message = "Nast name is required")
    private String lastName;
    @NotNull
    @NotBlank(message = "Email is required")
    @Email(message = "Email format not valid")
    private String email;
    //    @NotNull @NotBlank(message = "Birth date is required") @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
    @NotNull
    @NotBlank(message = "Photo is required")
    private String photo;

}
