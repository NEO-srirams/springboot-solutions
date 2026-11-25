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
import com.examly.springapp.dto.CourseSummaryDTO;
import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.dto.StudentResponseDTO;
import com.examly.springapp.dto.StudentSummaryDTO;
import com.examly.springapp.model.Course;
import com.examly.springapp.model.Student;
import com.examly.springapp.service.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
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
        if (course.getCredits() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Credits is required"));
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

    private CourseResponseDTO toCourseResponse(Course x) {
        List<StudentSummaryDTO> list = new ArrayList<>();
        for (Student y : x.getStudents()) {
            list.add(new StudentSummaryDTO(y.getId(), y.getName()));
        }
        list.sort(Comparator.comparing(StudentSummaryDTO::getId));
        return new CourseResponseDTO(x.getId(), x.getTitle(), x.getCode(), x.getCredits(), list);
    }
}
