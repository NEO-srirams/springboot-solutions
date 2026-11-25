package com.examly.springapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.Course;
import com.examly.springapp.model.Lesson;
import com.examly.springapp.service.CourseService;
import com.examly.springapp.service.LessonService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final LessonService lessonService;

    public CourseController(CourseService courseService, LessonService lessonService) {
        this.courseService = courseService;
        this.lessonService = lessonService;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.saveCourse(course));
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isPresent()) {
            return ResponseEntity.ok(course.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Course not found with id " + id));
    }

    @GetMapping("/{id}/lessons")
    public ResponseEntity<?> getLessonsByCourse(@PathVariable Long id) {
        if (courseService.getCourseById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Course not found with id " + id));
        }
        return ResponseEntity.ok(lessonService.getLessonsByCourseId(id));
    }

    @PostMapping("/{id}/lessons")
    public ResponseEntity<?> createLessonForCourse(@PathVariable Long id, @RequestBody Lesson lesson) {
        if (lesson.getTitle() == null || lesson.getTitle().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Title is required"));
        }
        if (lesson.getDurationMinutes() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Duration minutes is required"));
        }
        if (lesson.getSequenceNo() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Sequence no is required"));
        }
        Optional<Lesson> saved = lessonService.createLessonForCourse(id, lesson);
        if (saved.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(saved.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Course not found with id " + id));
    }
}
