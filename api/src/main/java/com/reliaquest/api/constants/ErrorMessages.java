package com.reliaquest.api.constants;

public class ErrorMessages {

    public static final String NO_EMPLOYEES_FOUND = "No employees found in the external service response.";
    public static final String NO_EMPLOYEES_FOR_SEARCH = "No employees found for search: ";
    public static final String EMPLOYEE_NOT_FOUND = "Employee with ID %s not found";
    public static final String EMPLOYEE_CREATION_FAILED =
            "Failed to create employee: No data returned from external service";
    public static final String EMPLOYEE_CREATION_INTERNAL_ERROR =
            "Failed to create employee due to an internal server error (HTTP %d)";
    public static final String NO_EMPLOYEES_TO_CALCULATE_HIGHEST_SALARY =
            "No employees found to calculate the highest salary.";
    public static final String NO_EMPLOYEES_TO_FETCH_TOP_EARNERS =
            "No employees found to fetch top 10 highest earning employee names.";
    public static final String EMPLOYEE_ALREADY_DELETED = "Employee with ID %s already deleted";
}
