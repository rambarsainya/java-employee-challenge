package com.reliaquest.api.exception.custom;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class EmployeeCreationExceptionTest {

    @Test
    void testEmployeeCreationExceptionWithMessage() {
        String expectedMessage = "Employee creation failed";

        EmployeeCreationException exception = new EmployeeCreationException(expectedMessage);

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void testEmployeeCreationExceptionWithMessageAndCause() {
        String expectedMessage = "Employee creation failed";
        Throwable cause = new Throwable("Cause of the error");

        EmployeeCreationException exception = new EmployeeCreationException(expectedMessage, cause);

        assertEquals(expectedMessage, exception.getMessage());

        assertEquals(cause, exception.getCause());
    }

    @Test
    void testEmployeeCreationExceptionWithCauseOnly() {
        Throwable cause = new Throwable("Cause of the error");

        EmployeeCreationException exception = new EmployeeCreationException(null, cause);

        assertEquals(cause, exception.getCause());

        assertNull(exception.getMessage());
    }
}
