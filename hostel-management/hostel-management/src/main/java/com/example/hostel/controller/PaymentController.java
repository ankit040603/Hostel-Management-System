package com.example.hostel.controller;

import com.example.hostel.dto.PaymentRequest;
import com.example.hostel.entity.Payment;
import com.example.hostel.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService service;
    public PaymentController(PaymentService service) { this.service = service; }

    @GetMapping
    public List<Payment> all() { return service.all(); }

    @GetMapping("/student/{studentId}")
    public List<Payment> forStudent(@PathVariable Long studentId) { return service.forStudent(studentId); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Payment record(@Valid @RequestBody PaymentRequest req) { return service.record(req); }
}
