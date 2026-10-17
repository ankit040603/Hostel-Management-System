package com.example.hostel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String studentId;   // institute roll / registration number

    @NotBlank
    private String name;

    @NotBlank @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Pattern(regexp = "^[0-9+\\- ]{7,15}$", message = "Invalid phone number")
    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String course;
    private String guardianName;
    private String guardianPhone;
    private String address;

    @Builder.Default
    private LocalDate registeredOn = LocalDate.now();
}
