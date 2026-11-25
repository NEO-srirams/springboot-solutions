package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Enrollment;
import com.examly.springapp.model.Student;
import com.examly.springapp.model.Course;

public interface EnrollmentService {

    boolean exists(Student student, Course course);

    Enrollment saveEnrollment(Enrollment enrollment, Student student, Course course);

    List<Enrollment> getEnrollmentsByStudent(Long studentId);

    List<Enrollment> getEnrollmentsByCourse(Long courseId);

    boolean deleteEnrollment(Long id);
}
