package com.example.hostel.service;

import com.example.hostel.dto.AllocationRequest;
import com.example.hostel.entity.Allocation;
import com.example.hostel.entity.Room;
import com.example.hostel.entity.Student;
import com.example.hostel.exception.BusinessException;
import com.example.hostel.exception.ResourceNotFoundException;
import com.example.hostel.repository.AllocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class AllocationService {
    private final AllocationRepository allocations;
    private final StudentService studentService;
    private final RoomService roomService;

    public AllocationService(AllocationRepository allocations, StudentService studentService, RoomService roomService) {
        this.allocations = allocations;
        this.studentService = studentService;
        this.roomService = roomService;
    }

    public Allocation allocate(AllocationRequest req) {
        Student student = studentService.findById(req.studentId());
        Room room = roomService.get(req.roomId());

        if (allocations.existsByStudentIdAndActiveTrue(student.getId()))
            throw new BusinessException("Student already has an active room allocation");
        if (allocations.countByRoomIdAndActiveTrue(room.getId()) >= room.getCapacity())
            throw new BusinessException("Room " + room.getRoomNumber() + " is full");

        return allocations.save(Allocation.builder()
                .student(student)
                .room(room)
                .checkInDate(req.checkInDate() != null ? req.checkInDate() : LocalDate.now())
                .active(true)
                .build());
    }

    public Allocation checkOut(Long allocationId) {
        Allocation a = allocations.findById(allocationId)
                .orElseThrow(() -> new ResourceNotFoundException("Allocation", allocationId));
        if (!a.isActive()) throw new BusinessException("Allocation is already closed");
        a.setActive(false);
        a.setCheckOutDate(LocalDate.now());
        return allocations.save(a);
    }

    @Transactional(readOnly = true)
    public List<Allocation> list(boolean activeOnly) {
        return activeOnly ? allocations.findByActiveTrue() : allocations.findAll();
    }

    @Transactional(readOnly = true)
    public List<Allocation> residentsOfRoom(Long roomId) {
        return allocations.findByRoomIdAndActiveTrue(roomId);
    }
}
