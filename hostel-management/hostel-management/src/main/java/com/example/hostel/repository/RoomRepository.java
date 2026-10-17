package com.example.hostel.repository;

import com.example.hostel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByRoomNumber(String roomNumber);

    @Query("select coalesce(sum(r.capacity), 0) from Room r")
    long totalCapacity();
}
