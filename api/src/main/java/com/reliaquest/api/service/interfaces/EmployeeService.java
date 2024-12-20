package com.reliaquest.api.service.interfaces;

import com.reliaquest.api.dto.CreateRequest;
import com.reliaquest.api.model.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> getEmployees();

    List<Employee> getEmployeesByNameSearch(String searchString);

    Employee getEmployeeById(String id);

    Optional<Integer> getHighestSalaryOfEmployees();

    List<String> getTopTenHighestEarningEmployeeNames();

    Employee createEmployee(CreateRequest employeeRequest);

    String deleteEmployeeById(String id);
}
