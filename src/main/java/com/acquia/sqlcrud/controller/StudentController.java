package com.acquia.sqlcrud.controller;


import com.acquia.sqlcrud.exception.ResourceNotFoundException;
import com.acquia.sqlcrud.model.Student;
import com.acquia.sqlcrud.service.StudentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@Validated
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getAllStudents() {
        try {
            log.info("Fetching all students");
            return studentService.getAllStudents();
        } catch (Exception e) {
            log.error("An error occurred while fetching all students: {}", e.getMessage());
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id) {
        try {
            log.info("Fetching student with id {}", id);
            Optional<Student> student = studentService.getStudentById(id);
            return student.map(ResponseEntity::ok).orElseThrow(() -> new ResourceNotFoundException("Student with id " + id + " not found"));
        } catch (Exception e) {
            log.error("An error occurred while fetching student with id {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        try {
            log.info("Creating new student");
            Student newStudent = studentService.createStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(newStudent);
        } catch (Exception e) {
            log.error("An error occurred while creating new student: {}", e.getMessage());
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @Valid @RequestBody Student student) {
        try {
            log.info("Updating student with id {}", id);
            Optional<Student> updatedStudent = studentService.updateStudent(id, student);
            return updatedStudent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("An error occurred while updating student with id {}: {}", id, e.getMessage());
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long id) {
        try {
            log.info("Deleting student with id {}", id);
            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("An error occurred while deleting student with id {}: {}", id, e.getMessage());
            throw e;
        }
    }
}
