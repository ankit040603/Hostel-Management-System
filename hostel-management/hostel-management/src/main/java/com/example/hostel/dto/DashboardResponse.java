package com.example.hostel.dto;

import java.math.BigDecimal;

public record DashboardResponse(long totalRooms, long totalStudents, long totalBeds, long occupiedBeds,
                                long availableBeds, double occupancyPercent, long openComplaints,
                                String month, BigDecimal collectionThisMonth) {}
