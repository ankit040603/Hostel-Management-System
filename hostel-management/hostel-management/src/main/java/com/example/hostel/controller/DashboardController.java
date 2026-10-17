package com.example.hostel.controller;

import com.example.hostel.dto.DashboardResponse;
import com.example.hostel.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService service;
    public DashboardController(DashboardService service) { this.service = service; }

    @GetMapping
    public DashboardResponse summary() { return service.summary(); }
}
