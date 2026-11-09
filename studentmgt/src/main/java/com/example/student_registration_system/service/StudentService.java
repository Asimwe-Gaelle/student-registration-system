package com.example.student_registration_system.service;

import com.example.student_registration_system.model.Student;
import com.example.student_registration_system.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public void registerStudent(Student student) {
        if (student.getAge() <= 18) {
            throw new IllegalArgumentException("Student must be older than 18.");
        }

        studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
