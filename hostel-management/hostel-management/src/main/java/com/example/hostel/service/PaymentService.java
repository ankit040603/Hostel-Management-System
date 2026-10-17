package com.example.hostel.service;

import com.example.hostel.dto.PaymentRequest;
import com.example.hostel.entity.Payment;
import com.example.hostel.exception.BusinessException;
import com.example.hostel.repository.AllocationRepository;
import com.example.hostel.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class PaymentService {
    private final PaymentRepository payments;
    private final AllocationRepository allocations;
    private final StudentService studentService;

    public PaymentService(PaymentRepository payments, AllocationRepository allocations, StudentService studentService) {
        this.payments = payments;
        this.allocations = allocations;
        this.studentService = studentService;
    }

    public Payment record(PaymentRequest req) {
        var student = studentService.findById(req.studentId());
        if (!allocations.existsByStudentIdAndActiveTrue(student.getId()))
            throw new BusinessException("Student has no active room allocation");
        if (payments.existsByStudentIdAndForMonth(student.getId(), req.forMonth()))
            throw new BusinessException("Fee for " + req.forMonth() + " is already recorded for this student");

        return payments.save(Payment.builder()
                .student(student)
                .amount(req.amount())
                .forMonth(req.forMonth())
                .method(req.method())
                .reference(req.reference())
                .paidOn(LocalDate.now())
                .build());
    }

    @Transactional(readOnly = true)
    public List<Payment> all() { return payments.findAll(); }

    @Transactional(readOnly = true)
    public List<Payment> forStudent(Long studentId) {
        studentService.findById(studentId);
        return payments.findByStudentIdOrderByPaidOnDesc(studentId);
    }
}
