package com.reliaquest.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeResponse<T> {

    private T data;
    private String status;
}
