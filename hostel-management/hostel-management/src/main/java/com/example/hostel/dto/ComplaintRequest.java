package com.example.hostel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ComplaintRequest(@NotNull Long studentId, @NotBlank String title, @NotBlank String description) {}
