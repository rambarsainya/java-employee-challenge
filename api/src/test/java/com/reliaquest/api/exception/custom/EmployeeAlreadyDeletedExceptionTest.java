package com.reliaquest.api.exception.custom;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class EmployeeAlreadyDeletedExceptionTest {

    @Test
    void testEmployeeAlreadyDeletedExceptionWithMessage() {
        String expectedMessage = "Employee already deleted";

        EmployeeAlreadyDeletedException exception = new EmployeeAlreadyDeletedException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testEmployeeAlreadyDeletedExceptionWithNullMessage() {
        EmployeeAlreadyDeletedException exception = new EmployeeAlreadyDeletedException(null);

        assertNull(exception.getMessage());
    }
}
