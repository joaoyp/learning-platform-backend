package com.joaoyp.learningplatformbackend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Duration;

public record CourseDTO(
        @NotBlank @NotNull String name,
        @NotBlank @NotNull String description,
        @NotBlank @NotNull String instructor,
        @NotBlank @NotNull String duration,
        @NotBlank @NotNull BigDecimal price) {
}
