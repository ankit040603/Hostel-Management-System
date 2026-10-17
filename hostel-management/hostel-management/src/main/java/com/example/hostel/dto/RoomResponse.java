package com.example.hostel.dto;

import com.example.hostel.entity.Room;
import com.example.hostel.entity.RoomType;

import java.math.BigDecimal;

public record RoomResponse(Long id, String roomNumber, String blockName, int floor, int capacity,
                           RoomType type, BigDecimal monthlyRent, long occupied, long available) {
    public static RoomResponse of(Room r, long occupied) {
        return new RoomResponse(r.getId(), r.getRoomNumber(), r.getBlockName(), r.getFloor(),
                r.getCapacity(), r.getType(), r.getMonthlyRent(), occupied, r.getCapacity() - occupied);
    }
}
