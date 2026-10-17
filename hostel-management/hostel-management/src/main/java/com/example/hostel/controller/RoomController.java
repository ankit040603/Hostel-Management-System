package com.example.hostel.controller;

import com.example.hostel.dto.RoomResponse;
import com.example.hostel.entity.Room;
import com.example.hostel.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    private final RoomService service;
    public RoomController(RoomService service) { this.service = service; }

    @GetMapping
    public List<RoomResponse> list(@RequestParam(defaultValue = "false") boolean availableOnly) {
        return service.findAll(availableOnly);
    }

    @GetMapping("/{id}")
    public RoomResponse get(@PathVariable Long id) { return service.findById(id); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public RoomResponse create(@Valid @RequestBody Room room) { return service.create(room); }

    @PutMapping("/{id}")
    public RoomResponse update(@PathVariable Long id, @Valid @RequestBody Room room) { return service.update(id, room); }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
