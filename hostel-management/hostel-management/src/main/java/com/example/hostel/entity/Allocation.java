package com.example.hostel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "allocations")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Allocation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Student student;

    @ManyToOne(optional = false)
    private Room room;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private boolean active;
}
