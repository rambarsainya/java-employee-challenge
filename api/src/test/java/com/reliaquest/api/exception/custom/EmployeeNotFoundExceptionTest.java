package com.reliaquest.api.exception.custom;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class EmployeeNotFoundExceptionTest {

    @Test
    void testEmployeeNotFoundExceptionWithMessage() {
        String expectedMessage = "Employee not found";

        EmployeeNotFoundException exception = new EmployeeNotFoundException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testEmployeeNotFoundExceptionWithNullMessage() {
        EmployeeNotFoundException exception = new EmployeeNotFoundException(null);

        assertNull(exception.getMessage());
    }
}
