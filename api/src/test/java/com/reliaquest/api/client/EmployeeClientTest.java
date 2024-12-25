package com.reliaquest.api.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

import com.reliaquest.api.dto.CreateRequest;
import com.reliaquest.api.dto.DeleteRequest;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeResponse;
import com.reliaquest.api.testdata.EmployeeTestDataHolder;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeClientTest {

    private static final String EXPECTED_EMPLOYEE_NAME = "Rajesh";

    @Mock
    private EmployeeClient employeeClient;

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
    void testGetAllEmployees() {
        EmployeeResponse<List<Employee>> mockResponse = new EmployeeResponse<>();
        mockResponse.setData(Collections.singletonList(mockEmployee));

        when(employeeClient.getAllEmployees()).thenReturn(mockResponse);

        EmployeeResponse<List<Employee>> response = employeeClient.getAllEmployees();

        assertNotNull(response);
        assertEquals(1, response.getData().size());
        assertEquals(EXPECTED_EMPLOYEE_NAME, response.getData().get(0).getEmployee_name());

        verify(employeeClient, times(1)).getAllEmployees();
    }

    @Test
    void testGetEmployeeById() {
        String id = mockEmployees.get(0).getId();
        EmployeeResponse<Employee> mockResponse = new EmployeeResponse<>();
        mockResponse.setData(mockEmployee);

        when(employeeClient.getEmployeeById(id)).thenReturn(mockResponse);

        EmployeeResponse<Employee> response = employeeClient.getEmployeeById(id);

        assertNotNull(response);
        assertEquals(EXPECTED_EMPLOYEE_NAME, response.getData().getEmployee_name());

        verify(employeeClient, times(1)).getEmployeeById(id);
    }

    @Test
    void testCreateEmployee() {
        CreateRequest createRequest = new CreateRequest();
        createRequest.setName("Rajesh");

        EmployeeResponse<Employee> mockResponse = new EmployeeResponse<>();
        mockResponse.setData(mockEmployee);

        when(employeeClient.createEmployee(createRequest)).thenReturn(mockResponse);

        EmployeeResponse<Employee> response = employeeClient.createEmployee(createRequest);

        assertNotNull(response);
        assertEquals(EXPECTED_EMPLOYEE_NAME, response.getData().getEmployee_name());

        verify(employeeClient, times(1)).createEmployee(createRequest);
    }

    @Test
    void testDeleteEmployeeByName() {
        DeleteRequest deleteRequest = new DeleteRequest("Rajesh");

        EmployeeResponse<Boolean> mockResponse = new EmployeeResponse<>();
        mockResponse.setData(true);

        when(employeeClient.deleteEmployeeByName(deleteRequest)).thenReturn(mockResponse);

        EmployeeResponse<Boolean> response = employeeClient.deleteEmployeeByName(deleteRequest);

        assertNotNull(response);
        assertTrue(response.getData());

        verify(employeeClient, times(1)).deleteEmployeeByName(deleteRequest);
    }
}
