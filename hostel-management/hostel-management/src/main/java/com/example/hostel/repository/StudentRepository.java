package com.example.hostel.repository;

import com.example.hostel.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByStudentId(String studentId);
    boolean existsByEmail(String email);
    List<Student> findByNameContainingIgnoreCase(String name);
}
