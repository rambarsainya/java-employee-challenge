package com.reliaquest.api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Positive(message = "Salary must be greater than zero") private Integer salary;

    @Min(value = 16, message = "Age must be at least 16")
    @Max(value = 75, message = "Age must be at most 75")
    private Integer age;

    @NotBlank(message = "Title is required")
    private String title;

    public CreateRequest() {}

    public CreateRequest(String name, Integer salary, Integer age, String title) {
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.title = title;
    }
}
