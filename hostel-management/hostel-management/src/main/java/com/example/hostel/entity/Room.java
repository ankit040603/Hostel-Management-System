package com.example.hostel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "rooms")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Room {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String roomNumber;

    private String blockName;

    @Min(0)
    private int floor;

    @Min(1)
    private int capacity;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoomType type;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal monthlyRent;
}
