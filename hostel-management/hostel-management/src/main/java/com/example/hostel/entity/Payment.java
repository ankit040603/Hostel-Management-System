package com.example.hostel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Student student;

    private BigDecimal amount;

    /** Billing month in yyyy-MM format, e.g. 2026-09 */
    private String forMonth;

    private LocalDate paidOn;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    private String reference;
}
