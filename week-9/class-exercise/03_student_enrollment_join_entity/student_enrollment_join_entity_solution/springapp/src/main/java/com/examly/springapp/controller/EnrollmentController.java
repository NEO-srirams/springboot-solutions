package com.examly.springapp.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.CourseResponseDTO;
import com.examly.springapp.dto.EnrollmentRequestDTO;
import com.examly.springapp.dto.EnrollmentResponseDTO;
import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.dto.StudentResponseDTO;
import com.examly.springapp.model.Course;
import com.examly.springapp.model.Enrollment;
import com.examly.springapp.model.Student;
import com.examly.springapp.service.CourseService;
import com.examly.springapp.service.EnrollmentService;
import com.examly.springapp.service.StudentService;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public EnrollmentController(StudentService studentService, CourseService courseService, EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<?> createEnrollment(@RequestBody EnrollmentRequestDTO request) {
        if (request.getStudentId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Student id is required"));
        }
        if (request.getCourseId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Course id is required"));
        }
        if (request.getEnrolledOn() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Enrolled on is required"));
        }
        Optional<Student> student = studentService.getStudentById(request.getStudentId());
        if (student.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Student not found with id " + request.getStudentId()));
        }
        Optional<Course> course = courseService.getCourseById(request.getCourseId());
        if (course.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Course not found with id " + request.getCourseId()));
        }
        if (enrollmentService.exists(student.get(), course.get())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Student is already enrolled in this course"));
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrolledOn(request.getEnrolledOn());
        enrollment.setGrade(request.getGrade());
        return ResponseEntity.status(HttpStatus.CREATED).body(toEnrollmentResponse(enrollmentService.saveEnrollment(enrollment, student.get(), course.get())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEnrollment(@PathVariable Long id) {
        if (enrollmentService.deleteEnrollment(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Enrollment not found with id " + id));
    }

    private EnrollmentResponseDTO toEnrollmentResponse(Enrollment x) {
        return new EnrollmentResponseDTO(x.getId(), x.getStudent().getId(), x.getStudent().getName(), x.getCourse().getId(), x.getCourse().getTitle(),
                x.getEnrolledOn(), x.getGrade());
    }
}
