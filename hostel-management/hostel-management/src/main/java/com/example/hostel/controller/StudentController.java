package com.example.hostel.controller;

import com.example.hostel.entity.Student;
import com.example.hostel.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService service;
    public StudentController(StudentService service) { this.service = service; }

    @GetMapping
    public List<Student> list(@RequestParam(required = false) String name) { return service.search(name); }

    @GetMapping("/{id}")
    public Student get(@PathVariable Long id) { return service.findById(id); }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public Student create(@Valid @RequestBody Student s) { return service.create(s); }

    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @Valid @RequestBody Student s) { return service.update(id, s); }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
