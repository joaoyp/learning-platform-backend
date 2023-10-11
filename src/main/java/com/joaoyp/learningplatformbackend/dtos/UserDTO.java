package com.joaoyp.learningplatformbackend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record UserDTO(
        @NotNull @NotBlank String username,
        @NotNull @NotBlank String password,
        @Email String email) {
}