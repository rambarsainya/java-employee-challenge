package com.reliaquest.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import com.reliaquest.api.dto.CreateRequest;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.service.interfaces.EmployeeService;
import com.reliaquest.api.testdata.EmployeeTestDataHolder;
import java.util.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private AutoCloseable mocks;

    private String employeeId;

    private List<Employee> mockEmployees;

    @BeforeEach
    void setUp() {
        mocks = openMocks(this);
        employeeId = UUID.randomUUID().toString();
        mockEmployees = EmployeeTestDataHolder.getMockEmployees();
    }

    @AfterEach
    void tearDown() throws Exception {
        mocks.close();
    }

    @Test
    void testGetAllEmployees() {
        when(employeeService.getEmployees()).thenReturn(mockEmployees);

        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(
                mockEmployees.size(), Objects.requireNonNull(response.getBody()).size());
        verify(employeeService, times(1)).getEmployees();
    }

    @Test
    void testGetEmployeesByNameSearch() {
        String searchString = "Rajesh";

        when(employeeService.getEmployeesByNameSearch(searchString)).thenReturn(mockEmployees);

        ResponseEntity<List<Employee>> response = employeeController.getEmployeesByNameSearch(searchString);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(
                mockEmployees.size(), Objects.requireNonNull(response.getBody()).size());
        verify(employeeService, times(1)).getEmployeesByNameSearch(searchString);
    }

    @Test
    void testGetEmployeeById() {
        Employee mockEmployee = mockEmployees.get(0);

        when(employeeService.getEmployeeById(employeeId)).thenReturn(mockEmployee);

        ResponseEntity<Employee> response = employeeController.getEmployeeById(employeeId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(
                mockEmployee.getId(), Objects.requireNonNull(response.getBody()).getId());
        verify(employeeService, times(1)).getEmployeeById(employeeId);
    }

    @Test
    void testGetHighestSalaryOfEmployees() {
        Optional<Integer> mockSalary = Optional.of(60000);

        when(employeeService.getHighestSalaryOfEmployees()).thenReturn(mockSalary);

        ResponseEntity<Integer> response = employeeController.getHighestSalaryOfEmployees();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockSalary.get(), response.getBody());
        verify(employeeService, times(1)).getHighestSalaryOfEmployees();
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        List<String> mockNames = Arrays.asList("Rajesh", "Ravi");

        when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(mockNames);

        ResponseEntity<List<String>> response = employeeController.getTopTenHighestEarningEmployeeNames();

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(
                mockNames.size(), Objects.requireNonNull(response.getBody()).size());
        verify(employeeService, times(1)).getTopTenHighestEarningEmployeeNames();
    }

    @Test
    void testCreateEmployee() {
        Employee mockEmployee = mockEmployees.get(0);
        CreateRequest mockRequest = EmployeeTestDataHolder.getMockCreateRequest();

        when(employeeService.createEmployee(mockRequest)).thenReturn(mockEmployee);

        ResponseEntity<Employee> response = employeeController.createEmployee(mockRequest);

        assertNotNull(response);
        assertEquals(201, response.getStatusCode().value());
        assertEquals(
                mockEmployee.getId(), Objects.requireNonNull(response.getBody()).getId());
        verify(employeeService, times(1)).createEmployee(mockRequest);
    }

    @Test
    void testDeleteEmployeeById() {
        String mockName = "Rajesh";

        when(employeeService.deleteEmployeeById(employeeId)).thenReturn(mockName);

        ResponseEntity<String> response = employeeController.deleteEmployeeById(employeeId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockName, response.getBody());
        verify(employeeService, times(1)).deleteEmployeeById(employeeId);
    }
}
