package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Course;
import com.examly.springapp.model.Student;

public interface StudentService {

    Student saveStudent(Student student);

    List<Student> getAllStudents();

    Optional<Student> getStudentById(Long id);

    boolean hasCourse(Long studentId, Long courseId);

    Student addCourse(Long studentId, Long courseId);

    Student removeCourse(Long studentId, Long courseId);

    void deleteStudent(Long id);
}
