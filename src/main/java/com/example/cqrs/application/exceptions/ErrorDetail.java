package com.example.cqrs.application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail {
    private ZonedDateTime errorDate;
    private String detail;
    private String message;
    private String title;
    private String errorKey;
    private Map<String, List<ValidationError>> errors = new HashMap<>();

}
