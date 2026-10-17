package com.example.hostel;

import com.example.hostel.dto.AllocationRequest;
import com.example.hostel.entity.*;
import com.example.hostel.exception.BusinessException;
import com.example.hostel.repository.RoomRepository;
import com.example.hostel.repository.StudentRepository;
import com.example.hostel.service.AllocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AllocationFlowTest {
    @Autowired RoomRepository rooms;
    @Autowired StudentRepository students;
    @Autowired AllocationService allocations;

    @Test
    void cannotOverfillRoom() {
        Room room = rooms.save(Room.builder().roomNumber("T-1").floor(0).capacity(1).type(RoomType.SINGLE).monthlyRent(BigDecimal.TEN).build());
        Student a = students.save(Student.builder().studentId("T-A").name("A").email("ta@x.com").build());
        Student b = students.save(Student.builder().studentId("T-B").name("B").email("tb@x.com").build());

        allocations.allocate(new AllocationRequest(a.getId(), room.getId(), null));
        assertThrows(BusinessException.class,
                () -> allocations.allocate(new AllocationRequest(b.getId(), room.getId(), null)));
    }
}
