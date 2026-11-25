package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.Enrollment;
import com.examly.springapp.model.Student;
import com.examly.springapp.model.Course;
import com.examly.springapp.repository.EnrollmentRepository;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public boolean exists(Student student, Course course) {
        return enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), course.getId());
    }

    @Override
    public Enrollment saveEnrollment(Enrollment enrollment, Student student, Course course) {
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        return enrollmentRepository.save(enrollment);
    }

    @Transactional
    @Override
    public List<Enrollment> getEnrollmentsByStudent(Long studentId) {
        return enrollmentRepository.findByStudentWithDetails(studentId);
    }

    @Transactional
    @Override
    public List<Enrollment> getEnrollmentsByCourse(Long courseId) {
        return enrollmentRepository.findByCourseWithDetails(courseId);
    }

    @Override
    public boolean deleteEnrollment(Long id) {
        if (!enrollmentRepository.existsById(id)) {
            return false;
        }
        enrollmentRepository.deleteById(id);
        return true;
    }
}
