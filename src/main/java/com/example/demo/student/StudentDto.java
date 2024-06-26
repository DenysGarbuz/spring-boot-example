package com.example.demo.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record StudentDto(
        @NotEmpty(
                message = "Firstname should not be empty"
        )
        String name,
        @NotEmpty(
                message = "Lastname  should not be empty"
        )
        String lastname,
        @Email()
        String email,
        Integer schoolId
) {
}
