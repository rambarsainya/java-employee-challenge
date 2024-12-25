package com.reliaquest.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Employee {

    private String id;
    private String employee_name;
    private int employee_salary;
    private int employee_age;
    private String employee_title;
    private String employee_email;

    public Employee(
            String id,
            String employee_name,
            int employee_salary,
            int employee_age,
            String employee_title,
            String employee_email) {
        this.id = id;
        this.employee_name = employee_name;
        this.employee_salary = employee_salary;
        this.employee_age = employee_age;
        this.employee_title = employee_title;
        this.employee_email = employee_email;
    }
}
