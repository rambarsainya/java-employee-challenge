package com.reliaquest.api.controller;

import com.reliaquest.api.dto.CreateRequest;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.interfaces.EmployeeService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling employee-related API requests.
 */
@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController implements IEmployeeController<Employee, CreateRequest> {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Handles the request to fetch all employees.
     *
     * @return a ResponseEntity containing a list of employees
     */
    @Override
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        logger.info("Received request to fetch all the employees.");

        // Fetch employees using the service
        List<Employee> employees = employeeService.getEmployees();

        logger.info("Fetched {} employees.", employees.size());
        return ResponseEntity.ok(employees); // 200 OK with the list of employees
    }

    /**
     * Handles the request to search for employees by name.
     *
     * @param searchString the search string to filter employee names
     * @return a ResponseEntity containing a list of matching employees
     */
    @Override
    @GetMapping("/search/{searchString}")
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) {
        logger.info("Received request to fetch employees with NAME: {}", searchString);

        // Check for null or empty search string
        if (searchString == null || searchString.trim().isEmpty()) {
            logger.warn("Invalid search string received: {}", searchString);
            return ResponseEntity.badRequest().build(); // 400 Bad Request for invalid search string
        }

        // Fetch employees using the service
        List<Employee> employees = employeeService.getEmployeesByNameSearch(searchString);

        logger.info("Found {} employees for search string: {}", employees.size(), searchString);
        return ResponseEntity.ok(employees); // 200 OK with the list of employees
    }

    /**
     * Handles the request to fetch an employee by their unique ID.
     *
     * @param id the ID of the employee to fetch
     * @return a ResponseEntity containing the employee details
     */
    @Override
    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        logger.info("Received request to fetch employee with ID: {}", id);

        // Fetch employee using the service
        Employee employee = employeeService.getEmployeeById(id);

        logger.info("Successfully retrieved employee details for ID: {}", id);
        return ResponseEntity.ok(employee); // Return employee details
    }

    /**
     * Handles the request to fetch the highest salary of all employees.
     *
     * @return a ResponseEntity containing the highest salary or 404 if no employees exist
     */
    @Override
    @GetMapping("/highestSalary")
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        logger.info("Received request to fetch the highest salary of employees.");

        // Fetch the highest salary from service
        Optional<Integer> highestSalary = employeeService.getHighestSalaryOfEmployees();

        if (highestSalary.isEmpty()) {
            logger.warn("No employees found. Unable to calculate the highest salary.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
        }

        logger.info("Successfully fetched the highest salary: {}", highestSalary.get());
        return ResponseEntity.ok(highestSalary.get()); // 200 OK with the highest salary
    }

    /**
     * Handles the request to fetch the top 10 highest earning employee names.
     *
     * @return a ResponseEntity containing a list of the top 10 highest earning employee names
     */
    @Override
    @GetMapping("/topTenHighestEarningEmployeeNames")
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        logger.info("Received request to fetch top 10 highest earning employee names.");

        // Fetch the top 10 highest earning employee names from service
        List<String> employeeNames = employeeService.getTopTenHighestEarningEmployeeNames();

        if (employeeNames.isEmpty()) {
            logger.info("No top earning employees found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404 Not Found
        }

        logger.info("Successfully fetched top 10 highest earning employee names.");
        return ResponseEntity.ok(employeeNames); // 200 OK with the list of employee names
    }

    /**
     * Handles the request to create a new employee.
     *
     * @param employeeRequest the request body containing the employee details to be created
     * @return a ResponseEntity containing the created employee
     */
    @Override
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody @Valid CreateRequest employeeRequest) {
        logger.info("Received request to create employee.");

        Employee response = employeeService.createEmployee(employeeRequest);
        logger.info("Successfully created employee with ID: {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // 201 Created
    }

    /**
     * Handles the request to delete an employee by their ID.
     *
     * @param id the ID of the employee to delete
     * @return a ResponseEntity containing the name of the deleted employee
     */
    @Override
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable String id) {
        logger.info("Received request to delete employee with ID: {}", id);

        // Call the service method to delete the employee
        String deletedEmployeeName = employeeService.deleteEmployeeById(id);

        logger.info("Successfully deleted employee with ID: {}", id);
        return ResponseEntity.ok(deletedEmployeeName);
    }
}
