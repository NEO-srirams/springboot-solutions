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
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final EnrollmentService enrollmentService;

    public StudentController(StudentService studentService, EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        if (student.getName() == null || student.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (student.getDepartment() == null || student.getDepartment().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Department is required"));
        }
        student.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toStudentResponse(studentService.saveStudent(student)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(toStudentResponse(student.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Student not found with id " + id));
    }

    @GetMapping("/{id}/enrollments")
    public ResponseEntity<?> getEnrollmentsByStudent(@PathVariable Long id) {
        if (studentService.getStudentById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Student not found with id " + id));
        }
        List<EnrollmentResponseDTO> result = new ArrayList<>();
        for (Enrollment x : enrollmentService.getEnrollmentsByStudent(id)) {
            result.add(toEnrollmentResponse(x));
        }
        return ResponseEntity.ok(result);
    }

    private StudentResponseDTO toStudentResponse(Student x) {
        return new StudentResponseDTO(x.getId(), x.getName(), x.getDepartment());
    }

    private EnrollmentResponseDTO toEnrollmentResponse(Enrollment x) {
        return new EnrollmentResponseDTO(x.getId(), x.getStudent().getId(), x.getStudent().getName(), x.getCourse().getId(), x.getCourse().getTitle(),
                x.getEnrolledOn(), x.getGrade());
    }
}
