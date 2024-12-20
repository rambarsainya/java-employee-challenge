package com.reliaquest.api.client;

import com.reliaquest.api.dto.CreateRequest;
import com.reliaquest.api.dto.DeleteRequest;
import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "employeeService", url = "http://localhost:8112/api/v1/employee")
public interface EmployeeClient {

    @GetMapping
    EmployeeResponse<List<Employee>> getAllEmployees();

    @GetMapping("/{id}")
    EmployeeResponse<Employee> getEmployeeById(@PathVariable String id);

    @PostMapping
    EmployeeResponse<Employee> createEmployee(@RequestBody CreateRequest createRequest);

    @DeleteMapping
    EmployeeResponse<Boolean> deleteEmployeeByName(@RequestBody DeleteRequest deleteRequest);
}
