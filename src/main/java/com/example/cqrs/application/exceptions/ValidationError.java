package com.example.cqrs.application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationError {
    private String code;
    private String message;
    private Map<String, List<ValidationError>> errors = new HashMap<>();
}
