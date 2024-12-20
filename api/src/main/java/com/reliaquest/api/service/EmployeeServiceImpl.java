package com.reliaquest.api.service;

import com.reliaquest.api.client.EmployeeClient;
import com.reliaquest.api.constants.ErrorMessages;
import com.reliaquest.api.dto.CreateRequest;
import com.reliaquest.api.dto.DeleteRequest;
import com.reliaquest.api.exception.custom.EmployeeAlreadyDeletedException;
import com.reliaquest.api.exception.custom.EmployeeCreationException;
import com.reliaquest.api.exception.custom.EmployeeNotFoundException;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeResponse;
import com.reliaquest.api.service.interfaces.EmployeeService;
import feign.FeignException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling employee-related operations,
 * including fetching employee data, creating new employees, deleting employees,
 * and calculating salaries.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeClient employeeClient;

    /**
     * Constructor to inject the EmployeeClient dependency.
     *
     * @param employeeClient the EmployeeClient used to interact with the external service
     */
    @Autowired
    public EmployeeServiceImpl(EmployeeClient employeeClient) {
        this.employeeClient = employeeClient;
    }

    /**
     * Fetches all employees from the external service.
     *
     * @return a list of all employees
     * @throws EmployeeNotFoundException if no employees are found
     */
    // @Cacheable(value = "allEmployeesCache")
    public List<Employee> getEmployees() {
        EmployeeResponse<List<Employee>> response = employeeClient.getAllEmployees();

        if (response == null || response.getData() == null || response.getData().isEmpty()) {
            throw new EmployeeNotFoundException(ErrorMessages.NO_EMPLOYEES_FOUND);
        }

        return response.getData();
    }

    /**
     * Fetches employees that match the search string based on their name.
     *
     * @param searchString the name search criteria
     * @return a list of employees whose names match the search string
     * @throws EmployeeNotFoundException if no matching employees are found
     */
    public List<Employee> getEmployeesByNameSearch(String searchString) {
        List<Employee> matchingEmployees = getEmployees().stream()
                .filter(employee -> employee.getEmployee_name().contains(searchString))
                .collect(Collectors.toList());

        if (matchingEmployees.isEmpty()) {
            throw new EmployeeNotFoundException(ErrorMessages.NO_EMPLOYEES_FOR_SEARCH + searchString);
        }

        return matchingEmployees;
    }

    /**
     * Fetches an employee by their ID.
     *
     * @param id the employee ID
     * @return the employee with the given ID
     * @throws EmployeeNotFoundException if no employee with the given ID is found
     */
    public Employee getEmployeeById(String id) {
        try {
            EmployeeResponse<Employee> response = employeeClient.getEmployeeById(id);

            if (response == null || response.getData() == null) {
                throw new EmployeeNotFoundException(String.format(ErrorMessages.EMPLOYEE_NOT_FOUND, id));
            }

            return response.getData();

        } catch (FeignException.NotFound e) {
            throw new EmployeeNotFoundException(String.format(ErrorMessages.EMPLOYEE_NOT_FOUND, id));

        } catch (FeignException e) {
            throw new RuntimeException("Error while fetching employee details", e);
        }
    }

    /**
     * Fetches the highest salary among the employees.
     *
     * @return the highest salary of the employees, wrapped in an Optional
     * @throws EmployeeNotFoundException if no employees are found to calculate the salary
     */
    public Optional<Integer> getHighestSalaryOfEmployees() {
        List<Employee> employees = getEmployees();

        if (employees == null || employees.isEmpty()) {
            throw new EmployeeNotFoundException(ErrorMessages.NO_EMPLOYEES_TO_CALCULATE_HIGHEST_SALARY);
        }

        return employees.stream().map(Employee::getEmployee_salary).reduce(Integer::max);
    }

    /**
     * Fetches the names of the top 10 highest earning employees.
     *
     * @return a list of the top 10 highest earning employee names
     * @throws EmployeeNotFoundException if no employees are found to calculate the top earners
     */
    public List<String> getTopTenHighestEarningEmployeeNames() {
        List<Employee> employees = getEmployees();

        if (employees == null || employees.isEmpty()) {
            throw new EmployeeNotFoundException(ErrorMessages.NO_EMPLOYEES_TO_FETCH_TOP_EARNERS);
        }

        return employees.stream()
                .sorted(Comparator.comparing(Employee::getEmployee_salary).reversed())
                .limit(10)
                .map(Employee::getEmployee_name)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new employee.
     *
     * @param employeeRequest the details of the employee to create
     * @return the created employee
     * @throws EmployeeCreationException if the employee creation fails
     */
    // @CacheEvict(value = "allEmployeesCache", allEntries = true)
    public Employee createEmployee(CreateRequest employeeRequest) {
        if (employeeRequest == null) {
            throw new IllegalArgumentException("Employee request cannot be null");
        }

        try {
            EmployeeResponse<Employee> response = employeeClient.createEmployee(employeeRequest);

            if (response != null && response.getData() != null) {
                return response.getData();
            } else {
                throw new EmployeeCreationException(ErrorMessages.EMPLOYEE_CREATION_FAILED);
            }

        } catch (FeignException e) {
            throw new EmployeeCreationException(
                    String.format(ErrorMessages.EMPLOYEE_CREATION_INTERNAL_ERROR, e.status()), e);
        }
    }

    /**
     * Deletes an employee by their ID.
     *
     * @param id the employee ID
     * @return the name of the deleted employee
     * @throws EmployeeNotFoundException if no employee with the given ID is found
     * @throws EmployeeAlreadyDeletedException if the employee has already been deleted
     */
    // @CacheEvict(value = "allEmployeesCache", allEntries = true)
    public String deleteEmployeeById(String id) {
        Employee employee = getEmployeeById(id);

        if (employee == null) {
            throw new EmployeeNotFoundException(String.format(ErrorMessages.EMPLOYEE_NOT_FOUND, id));
        }

        String employeeName = employee.getEmployee_name();
        DeleteRequest deleteRequest = new DeleteRequest(employeeName);

        EmployeeResponse<Boolean> response = employeeClient.deleteEmployeeByName(deleteRequest);

        if (response.getData()) {
            return employeeName;
        } else {
            throw new EmployeeAlreadyDeletedException(String.format(ErrorMessages.EMPLOYEE_ALREADY_DELETED, id));
        }
    }
}
