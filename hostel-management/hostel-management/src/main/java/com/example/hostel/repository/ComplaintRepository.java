package com.example.hostel.repository;

import com.example.hostel.entity.Complaint;
import com.example.hostel.entity.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByStatus(ComplaintStatus status);
    List<Complaint> findByStudentId(Long studentId);
    long countByStatus(ComplaintStatus status);
}
