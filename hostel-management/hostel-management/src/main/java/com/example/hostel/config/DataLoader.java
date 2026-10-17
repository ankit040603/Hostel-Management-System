package com.example.hostel.config;

import com.example.hostel.dto.AllocationRequest;
import com.example.hostel.entity.*;
import com.example.hostel.repository.RoomRepository;
import com.example.hostel.repository.StudentRepository;
import com.example.hostel.service.AllocationService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/** Seeds sample data on first start so the API has something to show. */
@Component
public class DataLoader implements CommandLineRunner {
    private final RoomRepository rooms;
    private final StudentRepository students;
    private final AllocationService allocations;

    public DataLoader(RoomRepository rooms, StudentRepository students, AllocationService allocations) {
        this.rooms = rooms;
        this.students = students;
        this.allocations = allocations;
    }

    @Override
    public void run(String... args) {
        if (rooms.count() > 0) return;

        Room a101 = rooms.save(Room.builder().roomNumber("A-101").blockName("A").floor(1).capacity(2).type(RoomType.DOUBLE).monthlyRent(new BigDecimal("3500")).build());
        rooms.save(Room.builder().roomNumber("A-102").blockName("A").floor(1).capacity(1).type(RoomType.SINGLE).monthlyRent(new BigDecimal("5000")).build());
        rooms.save(Room.builder().roomNumber("B-201").blockName("B").floor(2).capacity(3).type(RoomType.TRIPLE).monthlyRent(new BigDecimal("2500")).build());

        Student s1 = students.save(Student.builder().studentId("CS2026001").name("Ravi Kumar").email("ravi@example.com")
                .phone("9876543210").gender(Gender.MALE).course("B.Tech CSE").guardianName("Suresh Kumar").guardianPhone("9876500000").address("Imphal").build());
        students.save(Student.builder().studentId("EE2026014").name("Amit Singh").email("amit@example.com")
                .phone("9123456780").gender(Gender.MALE).course("B.Tech EEE").build());

        allocations.allocate(new AllocationRequest(s1.getId(), a101.getId(), null));
    }
}
