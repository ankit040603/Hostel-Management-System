package com.example.hostel.repository;

import com.example.hostel.entity.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AllocationRepository extends JpaRepository<Allocation, Long> {
    long countByRoomIdAndActiveTrue(Long roomId);
    long countByActiveTrue();
    boolean existsByStudentIdAndActiveTrue(Long studentId);
    List<Allocation> findByActiveTrue();
    List<Allocation> findByRoomIdAndActiveTrue(Long roomId);
}
