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
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    public CourseController(CourseService courseService, EnrollmentService enrollmentService) {
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        if (course.getTitle() == null || course.getTitle().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Title is required"));
        }
        if (course.getCode() == null || course.getCode().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Code is required"));
        }
        course.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toCourseResponse(courseService.saveCourse(course)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isPresent()) {
            return ResponseEntity.ok(toCourseResponse(course.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Course not found with id " + id));
    }

    @GetMapping("/{id}/enrollments")
    public ResponseEntity<?> getEnrollmentsByCourse(@PathVariable Long id) {
        if (courseService.getCourseById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Course not found with id " + id));
        }
        List<EnrollmentResponseDTO> result = new ArrayList<>();
        for (Enrollment x : enrollmentService.getEnrollmentsByCourse(id)) {
            result.add(toEnrollmentResponse(x));
        }
        return ResponseEntity.ok(result);
    }

    private CourseResponseDTO toCourseResponse(Course x) {
        return new CourseResponseDTO(x.getId(), x.getTitle(), x.getCode());
    }

    private EnrollmentResponseDTO toEnrollmentResponse(Enrollment x) {
        return new EnrollmentResponseDTO(x.getId(), x.getStudent().getId(), x.getStudent().getName(), x.getCourse().getId(), x.getCourse().getTitle(),
                x.getEnrolledOn(), x.getGrade());
    }
}
