package com.example.hostel.dto;

import com.example.hostel.entity.PaymentMethod;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PaymentRequest(
        @NotNull Long studentId,
        @NotNull @DecimalMin(value = "0.01") BigDecimal amount,
        @NotBlank @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$", message = "forMonth must be yyyy-MM") String forMonth,
        @NotNull PaymentMethod method,
        String reference) {}
