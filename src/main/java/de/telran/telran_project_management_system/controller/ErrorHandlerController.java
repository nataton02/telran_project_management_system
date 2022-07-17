package de.telran.telran_project_management_system.controller;

import de.telran.telran_project_management_system.dto.ErrorResponseDTO;
import de.telran.telran_project_management_system.dto.InputValidationResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<InputValidationResponseDTO> handleInputError(MethodArgumentNotValidException ex) {

        var responseBody = ex.getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(
                                DefaultMessageSourceResolvable::getDefaultMessage,
                                Collectors.toList()
                        )
                ));

        InputValidationResponseDTO response = InputValidationResponseDTO.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .errors(responseBody)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponseDTO> handle(ResponseStatusException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ErrorResponseDTO.builder()
                        .status(ex.getStatus())
                        .message(ex.getReason())
                        .build()
                );
    }
}
