package com.reliaquest.api.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import com.reliaquest.api.client.EmployeeClient;
import com.reliaquest.api.constants.ErrorMessages;
import com.reliaquest.api.dto.CreateRequest;
import com.reliaquest.api.dto.DeleteRequest;
import com.reliaquest.api.exception.custom.EmployeeAlreadyDeletedException;
import com.reliaquest.api.exception.custom.EmployeeCreationException;
import com.reliaquest.api.exception.custom.EmployeeNotFoundException;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeResponse;
import com.reliaquest.api.testdata.EmployeeTestDataHolder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class EmployeeServiceImplTest {

    private static final String EXPECTED_EMPLOYEE_NAME = "Rajesh";

    private static final Integer EXPECTED_EMPLOYEE_SALARY = 500000;

    @Mock
    private EmployeeClient employeeClient;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private AutoCloseable mocks;

    private List<Employee> mockEmployees;

    private Employee mockEmployee;

    @BeforeEach
    void setUp() {
        mocks = openMocks(this);
        mockEmployees = EmployeeTestDataHolder.getMockEmployees();
        mockEmployee = mockEmployees.get(0);
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void getEmployees_Success() {
        EmployeeResponse<List<Employee>> mockResponse = new EmployeeResponse<>(mockEmployees, "success");
        when(employeeClient.getAllEmployees()).thenReturn(mockResponse);

        List<Employee> employees = employeeService.getEmployees();
        assertEquals(3, employees.size());
        assertEquals(EXPECTED_EMPLOYEE_NAME, employees.get(0).getEmployee_name());
    }

    @Test
    void getEmployees_ThrowsEmployeeNotFoundException() {
        EmployeeResponse<List<Employee>> mockResponse = new EmployeeResponse<>(Collections.emptyList(), "success");
        when(employeeClient.getAllEmployees()).thenReturn(mockResponse);

        EmployeeNotFoundException exception =
                assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployees());
        assertEquals(ErrorMessages.NO_EMPLOYEES_FOUND, exception.getMessage());
    }

    @Test
    void getEmployeeById_Success() {
        String id = mockEmployee.getId();
        EmployeeResponse<Employee> mockResponse = new EmployeeResponse<>(mockEmployee, "success");
        when(employeeClient.getEmployeeById(id)).thenReturn(mockResponse);

        Employee employee = employeeService.getEmployeeById(id);
        assertNotNull(employee);
        assertEquals(EXPECTED_EMPLOYEE_NAME, employee.getEmployee_name());
    }

    @Test
    void getEmployeeById_ThrowsEmployeeNotFoundException() {
        String id = "1";
        EmployeeResponse<Employee> mockResponse = new EmployeeResponse<>(null, "failure");
        when(employeeClient.getEmployeeById(id)).thenReturn(mockResponse);

        EmployeeNotFoundException exception =
                assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(id));
        assertTrue(exception.getMessage().contains("Employee with ID 1 not found"));
    }

    @Test
    void createEmployee_Success() {
        CreateRequest createRequest = EmployeeTestDataHolder.getMockCreateRequest();
        EmployeeResponse<Employee> mockResponse = new EmployeeResponse<>(mockEmployee, "success");

        when(employeeClient.createEmployee(createRequest)).thenReturn(mockResponse);

        Employee createdEmployee = employeeService.createEmployee(createRequest);
        assertNotNull(createdEmployee);
        assertEquals(EXPECTED_EMPLOYEE_NAME, createdEmployee.getEmployee_name());
    }

    @Test
    void createEmployee_ThrowsEmployeeCreationException() {
        CreateRequest createRequest = EmployeeTestDataHolder.getMockCreateRequest();

        when(employeeClient.createEmployee(createRequest))
                .thenThrow(new EmployeeCreationException(ErrorMessages.EMPLOYEE_CREATION_INTERNAL_ERROR));

        EmployeeCreationException exception =
                assertThrows(EmployeeCreationException.class, () -> employeeService.createEmployee(createRequest));
        assertTrue(exception.getMessage().contains("Failed to create employee due to an internal server error"));
    }

    @Test
    void deleteEmployeeById_Success() {
        String id = mockEmployee.getId();
        String name = mockEmployee.getEmployee_name();
        DeleteRequest deleteRequest = new DeleteRequest(name);
        EmployeeResponse<Boolean> mockResponse = new EmployeeResponse<>(true, "success");

        when(employeeClient.getEmployeeById(id)).thenReturn(new EmployeeResponse<>(mockEmployee, "success"));
        when(employeeClient.deleteEmployeeByName(deleteRequest)).thenReturn(mockResponse);

        String deletedEmployeeName = employeeService.deleteEmployeeById(id);
        assertEquals(EXPECTED_EMPLOYEE_NAME, deletedEmployeeName);
    }

    @Test
    void deleteEmployeeById_ThrowsEmployeeAlreadyDeletedException() {
        String id = mockEmployee.getId();
        DeleteRequest deleteRequest = new DeleteRequest("Rajesh");
        EmployeeResponse<Boolean> mockResponse = new EmployeeResponse<>(false, "failure");

        when(employeeClient.getEmployeeById(id)).thenReturn(new EmployeeResponse<>(mockEmployee, "success"));
        when(employeeClient.deleteEmployeeByName(deleteRequest)).thenReturn(mockResponse);

        EmployeeAlreadyDeletedException exception =
                assertThrows(EmployeeAlreadyDeletedException.class, () -> employeeService.deleteEmployeeById(id));
        assertTrue(exception.getMessage().contains("already deleted"));
    }

    @Test
    void getHighestSalaryOfEmployees_Success() {
        EmployeeResponse<List<Employee>> mockResponse = new EmployeeResponse<>(mockEmployees, "success");
        when(employeeClient.getAllEmployees()).thenReturn(mockResponse);

        Optional<Integer> highestSalary = employeeService.getHighestSalaryOfEmployees();
        assertTrue(highestSalary.isPresent());
        assertEquals(EXPECTED_EMPLOYEE_SALARY, highestSalary.get());
    }

    @Test
    void getTopTenHighestEarningEmployeeNames_Success() {
        EmployeeResponse<List<Employee>> mockResponse = new EmployeeResponse<>(mockEmployees, "success");
        when(employeeClient.getAllEmployees()).thenReturn(mockResponse);

        List<String> topEarningNames = employeeService.getTopTenHighestEarningEmployeeNames();
        assertEquals(3, topEarningNames.size());
        assertEquals(EXPECTED_EMPLOYEE_NAME, topEarningNames.get(0));
    }
}
