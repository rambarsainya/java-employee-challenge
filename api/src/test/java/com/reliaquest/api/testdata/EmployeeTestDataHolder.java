package com.reliaquest.api.testdata;

import com.reliaquest.api.dto.CreateRequest;
import com.reliaquest.api.model.Employee;
import java.util.ArrayList;
import java.util.List;

public class EmployeeTestDataHolder {

    private static final String id = "7d073989-95f2-4119-bd05-7196046a45a4";
    private static final String employee_name = "Rajesh";
    private static final int employee_salary = 500000;
    private static final int employee_age = 31;
    private static final String employee_title = "Manager";
    private static final String employee_email = "rajesh@gmail.com";

    private static final String id2 = "327eb4f2-e07b-405b-b690-1e193ca57a13";
    private static final String employee_name2 = "Ravi";
    private static final int employee_salary2 = 200000;
    private static final int employee_age2 = 25;
    private static final String employee_title2 = "Engineer";
    private static final String employee_email2 = "ravi@gmail.com";

    private static final String id3 = "43b8cc30-1386-411b-a35e-1ca6439327dd";
    private static final String employee_name3 = "Ramesh";
    private static final int employee_salary3 = 300000;
    private static final int employee_age3 = 28;
    private static final String employee_title3 = "Senior Engineer";
    private static final String employee_email3 = "ramesh@gmail.com";

    public static List<Employee> getMockEmployees() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(id, employee_name, employee_salary, employee_age, employee_title, employee_email));
        employees.add(
                new Employee(id2, employee_name2, employee_salary2, employee_age2, employee_title2, employee_email2));
        employees.add(
                new Employee(id3, employee_name3, employee_salary3, employee_age3, employee_title3, employee_email3));
        return employees;
    }

    public static CreateRequest getMockCreateRequest() {
        return new CreateRequest(employee_name, employee_salary, employee_age, employee_title);
    }
}
