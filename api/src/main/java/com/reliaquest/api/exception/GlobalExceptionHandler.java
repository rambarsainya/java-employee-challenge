package com.reliaquest.api.exception;

import com.reliaquest.api.dto.ErrorResponse;
import com.reliaquest.api.exception.custom.EmployeeAlreadyDeletedException;
import com.reliaquest.api.exception.custom.EmployeeCreationException;
import com.reliaquest.api.exception.custom.EmployeeNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeCreationException.class)
    public ResponseEntity<Object> handleEmployeeCreationException(EmployeeCreationException ex) {
        // Custom error response for EmployeeCreationException
        ErrorResponse errorResponse = new ErrorResponse("Employee Not Created", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeNotFoundException(EmployeeNotFoundException ex) {
        // Custom error response for EmployeeNotFoundException
        ErrorResponse errorResponse = new ErrorResponse("Employee Not Found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(EmployeeAlreadyDeletedException.class)
    public ResponseEntity<Object> handleEmployeeAlreadyDeletedException(EmployeeAlreadyDeletedException ex) {
        // Custom error response for EmployeeAlreadyDeletedException
        ErrorResponse errorResponse = new ErrorResponse("Employee Already Deleted", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        // Handle generic exceptions
        ErrorResponse errorResponse = new ErrorResponse("Internal Server Error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
