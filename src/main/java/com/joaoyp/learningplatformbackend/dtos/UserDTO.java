package com.joaoyp.learningplatformbackend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(
        @NotNull @NotBlank String username,
        @NotNull @NotBlank String password,
        @Email @NotNull @NotBlank String email) {
}