package com.example.hostel.service;

import com.example.hostel.dto.DashboardResponse;
import com.example.hostel.entity.ComplaintStatus;
import com.example.hostel.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;

@Service
@Transactional(readOnly = true)
public class DashboardService {
    private final RoomRepository rooms;
    private final StudentRepository students;
    private final AllocationRepository allocations;
    private final ComplaintRepository complaints;
    private final PaymentRepository payments;

    public DashboardService(RoomRepository rooms, StudentRepository students, AllocationRepository allocations,
                            ComplaintRepository complaints, PaymentRepository payments) {
        this.rooms = rooms;
        this.students = students;
        this.allocations = allocations;
        this.complaints = complaints;
        this.payments = payments;
    }

    public DashboardResponse summary() {
        long beds = rooms.totalCapacity();
        long occupied = allocations.countByActiveTrue();
        String month = YearMonth.now().toString();
        double pct = beds == 0 ? 0 : Math.round(occupied * 1000.0 / beds) / 10.0;
        return new DashboardResponse(rooms.count(), students.count(), beds, occupied, beds - occupied, pct,
                complaints.countByStatus(ComplaintStatus.OPEN), month, payments.totalForMonth(month));
    }
}
