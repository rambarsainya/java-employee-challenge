package com.reliaquest.api.exception.custom;

public class EmployeeAlreadyDeletedException extends RuntimeException {

    public EmployeeAlreadyDeletedException(String message) {
        super(message);
    }
}
