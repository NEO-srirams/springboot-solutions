package com.examly.springapp.service;

import java.util.Optional;

import com.examly.springapp.model.Student;

public interface StudentService {

    Student saveStudent(Student student);

    Optional<Student> getStudentById(Long id);
}
