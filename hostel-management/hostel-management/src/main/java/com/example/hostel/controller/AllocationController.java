package com.example.hostel.controller;

import com.example.hostel.dto.AllocationRequest;
import com.example.hostel.entity.Allocation;
import com.example.hostel.service.AllocationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/allocations")
public class AllocationController {
    private final AllocationService service;
    public AllocationController(AllocationService service) { this.service = service; }

    @GetMapping
    public List<Allocation> list(@RequestParam(defaultValue = "true") boolean activeOnly) { return service.list(activeOnly); }

    @GetMapping("/room/{roomId}")
    public List<Allocation> residents(@PathVariable Long roomId) { return service.residentsOfRoom(roomId); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Allocation allocate(@Valid @RequestBody AllocationRequest req) { return service.allocate(req); }

    @PostMapping("/{id}/checkout")
    public Allocation checkOut(@PathVariable Long id) { return service.checkOut(id); }
}
