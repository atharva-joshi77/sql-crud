package com.acquia.sqlcrud.service;

import com.acquia.sqlcrud.exception.ResourceNotFoundException;
import com.acquia.sqlcrud.model.Student;
import com.acquia.sqlcrud.repository.StudentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private static final Logger logger = LogManager.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAllStudents();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findStudentById(id);
    }

    public Student createStudent(Student student) {
        return studentRepository.saveStudent(student.getId(), student.getFirstName(), student.getLastName(), student.getEmail(),
                student.getCourse());
    }

    public Optional<Student> updateStudent(Long id, Student studentDetails) {
        if (studentRepository.findStudentById(id).isEmpty()) {
            throw new ResourceNotFoundException("Student with id " + id + " not found");
        }
        return studentRepository.updateStudent(id, studentDetails.getFirstName(), studentDetails.getLastName(),
                studentDetails.getEmail(), studentDetails.getCourse());
    }

    public void deleteStudent(Long id) {
        if (studentRepository.findStudentById(id).isEmpty()) {
            throw new ResourceNotFoundException("Student with id " + id + " not found");
        }
        studentRepository.deleteStudent(id);
    }
}

