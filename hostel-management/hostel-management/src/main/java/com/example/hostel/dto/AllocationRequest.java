package com.example.hostel.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AllocationRequest(@NotNull Long studentId, @NotNull Long roomId, LocalDate checkInDate) {}
