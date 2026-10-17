package com.example.hostel.controller;

import com.example.hostel.dto.ComplaintRequest;
import com.example.hostel.entity.Complaint;
import com.example.hostel.entity.ComplaintStatus;
import com.example.hostel.service.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {
    private final ComplaintService service;
    public ComplaintController(ComplaintService service) { this.service = service; }

    @GetMapping
    public List<Complaint> list(@RequestParam(required = false) ComplaintStatus status) { return service.list(status); }

    @GetMapping("/student/{studentId}")
    public List<Complaint> forStudent(@PathVariable Long studentId) { return service.forStudent(studentId); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Complaint create(@Valid @RequestBody ComplaintRequest req) { return service.create(req); }

    @PatchMapping("/{id}/status")
    public Complaint updateStatus(@PathVariable Long id, @RequestParam ComplaintStatus status) {
        return service.updateStatus(id, status);
    }
}
