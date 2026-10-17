package com.example.hostel.service;

import com.example.hostel.dto.RoomResponse;
import com.example.hostel.entity.Room;
import com.example.hostel.exception.BusinessException;
import com.example.hostel.exception.ResourceNotFoundException;
import com.example.hostel.repository.AllocationRepository;
import com.example.hostel.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoomService {
    private final RoomRepository rooms;
    private final AllocationRepository allocations;

    public RoomService(RoomRepository rooms, AllocationRepository allocations) {
        this.rooms = rooms;
        this.allocations = allocations;
    }

    @Transactional(readOnly = true)
    public List<RoomResponse> findAll(boolean onlyAvailable) {
        return rooms.findAll().stream()
                .map(this::toResponse)
                .filter(r -> !onlyAvailable || r.available() > 0)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoomResponse findById(Long id) {
        return toResponse(get(id));
    }

    public RoomResponse create(Room room) {
        if (rooms.existsByRoomNumber(room.getRoomNumber()))
            throw new BusinessException("Room number already exists: " + room.getRoomNumber());
        room.setId(null);
        return toResponse(rooms.save(room));
    }

    public RoomResponse update(Long id, Room in) {
        Room room = get(id);
        long occupied = allocations.countByRoomIdAndActiveTrue(id);
        if (in.getCapacity() < occupied)
            throw new BusinessException("Capacity cannot be less than current occupancy (" + occupied + ")");
        if (!room.getRoomNumber().equals(in.getRoomNumber()) && rooms.existsByRoomNumber(in.getRoomNumber()))
            throw new BusinessException("Room number already exists: " + in.getRoomNumber());
        room.setRoomNumber(in.getRoomNumber());
        room.setBlockName(in.getBlockName());
        room.setFloor(in.getFloor());
        room.setCapacity(in.getCapacity());
        room.setType(in.getType());
        room.setMonthlyRent(in.getMonthlyRent());
        return toResponse(rooms.save(room));
    }

    public void delete(Long id) {
        Room room = get(id);
        if (allocations.countByRoomIdAndActiveTrue(id) > 0)
            throw new BusinessException("Cannot delete a room that has active residents");
        rooms.delete(room);
    }

    Room get(Long id) {
        return rooms.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room", id));
    }

    private RoomResponse toResponse(Room r) {
        return RoomResponse.of(r, allocations.countByRoomIdAndActiveTrue(r.getId()));
    }
}
