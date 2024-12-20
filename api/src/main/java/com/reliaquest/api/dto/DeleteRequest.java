package com.reliaquest.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeleteRequest {

    @NotBlank(message = "Name is required")
    private String name;

    public DeleteRequest(String name) {
        this.name = name;
    }
}
