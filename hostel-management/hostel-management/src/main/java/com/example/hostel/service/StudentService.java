package com.example.hostel.service;

import com.example.hostel.entity.Student;
import com.example.hostel.exception.BusinessException;
import com.example.hostel.exception.ResourceNotFoundException;
import com.example.hostel.repository.AllocationRepository;
import com.example.hostel.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService {
    private final StudentRepository students;
    private final AllocationRepository allocations;

    public StudentService(StudentRepository students, AllocationRepository allocations) {
        this.students = students;
        this.allocations = allocations;
    }

    @Transactional(readOnly = true)
    public List<Student> search(String name) {
        return (name == null || name.isBlank()) ? students.findAll() : students.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return students.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student", id));
    }

    public Student create(Student s) {
        if (students.existsByStudentId(s.getStudentId()))
            throw new BusinessException("Student ID already registered: " + s.getStudentId());
        if (students.existsByEmail(s.getEmail()))
            throw new BusinessException("Email already registered: " + s.getEmail());
        s.setId(null);
        return students.save(s);
    }

    public Student update(Long id, Student in) {
        Student s = findById(id);
        if (!s.getStudentId().equals(in.getStudentId()) && students.existsByStudentId(in.getStudentId()))
            throw new BusinessException("Student ID already registered: " + in.getStudentId());
        if (!s.getEmail().equals(in.getEmail()) && students.existsByEmail(in.getEmail()))
            throw new BusinessException("Email already registered: " + in.getEmail());
        s.setStudentId(in.getStudentId());
        s.setName(in.getName());
        s.setEmail(in.getEmail());
        s.setPhone(in.getPhone());
        s.setGender(in.getGender());
        s.setCourse(in.getCourse());
        s.setGuardianName(in.getGuardianName());
        s.setGuardianPhone(in.getGuardianPhone());
        s.setAddress(in.getAddress());
        return students.save(s);
    }

    public void delete(Long id) {
        Student s = findById(id);
        if (allocations.existsByStudentIdAndActiveTrue(id))
            throw new BusinessException("Check the student out of their room before deleting");
        students.delete(s);
    }
}
