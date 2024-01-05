package com.example.cqrs.application.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String NOT_FOUND = "Not found";

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleBusinessException(PersonNotFoundException exception) {
        var errorDetail = ErrorDetail.builder()
                .errorDate(ZonedDateTime.now())
                .title(NOT_FOUND)
              //  .detail(exception.getErrorEnum().getErrorMessage())
              //  .errorKey(exception.getErrorEnum().getErrorKey())
                .message(exception.getClass().getName())
                .build();
        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InputFieldException.class)
    public ResponseEntity<Map<String, String>> handleInputFieldException(InputFieldException exception) {
        InputFieldException inputFieldException = new InputFieldException(exception.getBindingResult());
        return ResponseEntity.badRequest().body(inputFieldException.getErrorsMap());
    }

}
