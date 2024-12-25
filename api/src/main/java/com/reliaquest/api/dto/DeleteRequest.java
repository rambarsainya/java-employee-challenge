package com.reliaquest.api.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        DeleteRequest that = (DeleteRequest) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
