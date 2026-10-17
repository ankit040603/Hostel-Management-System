package com.example.hostel.service;

import com.example.hostel.dto.ComplaintRequest;
import com.example.hostel.entity.Complaint;
import com.example.hostel.entity.ComplaintStatus;
import com.example.hostel.exception.ResourceNotFoundException;
import com.example.hostel.repository.ComplaintRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ComplaintService {
    private final ComplaintRepository complaints;
    private final StudentService studentService;

    public ComplaintService(ComplaintRepository complaints, StudentService studentService) {
        this.complaints = complaints;
        this.studentService = studentService;
    }

    public Complaint create(ComplaintRequest req) {
        return complaints.save(Complaint.builder()
                .student(studentService.findById(req.studentId()))
                .title(req.title())
                .description(req.description())
                .status(ComplaintStatus.OPEN)
                .createdAt(LocalDateTime.now())
                .build());
    }

    public Complaint updateStatus(Long id, ComplaintStatus status) {
        Complaint c = complaints.findById(id).orElseThrow(() -> new ResourceNotFoundException("Complaint", id));
        c.setStatus(status);
        c.setResolvedAt(status == ComplaintStatus.RESOLVED ? LocalDateTime.now() : null);
        return complaints.save(c);
    }

    @Transactional(readOnly = true)
    public List<Complaint> list(ComplaintStatus status) {
        return status == null ? complaints.findAll() : complaints.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Complaint> forStudent(Long studentId) {
        return complaints.findByStudentId(studentId);
    }
}
