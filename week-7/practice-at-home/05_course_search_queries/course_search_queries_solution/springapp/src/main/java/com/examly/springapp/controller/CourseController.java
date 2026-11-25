package com.examly.springapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.Course;
import com.examly.springapp.service.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<List<Course>> getCourseByLevel(@PathVariable String level) {
        return ResponseEntity.ok(courseService.getCourseByLevel(level));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Course>> searchCourse(@RequestParam String keyword) {
        return ResponseEntity.ok(courseService.searchCourse(keyword));
    }

    @GetMapping("/fee-range")
    public ResponseEntity<?> getCourseByFeeRange(@RequestParam Double min, @RequestParam Double max) {
        if (min > max) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Minimum must not be greater than maximum"));
        }
        return ResponseEntity.ok(courseService.getCourseByFeeRange(min, max));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Course>> filterCourse(@RequestParam String level, @RequestParam Double minFee) {
        return ResponseEntity.ok(courseService.filterCourse(level, minFee));
    }

    @GetMapping("/either")
    public ResponseEntity<List<Course>> eitherCourse(@RequestParam String level, @RequestParam String keyword) {
        return ResponseEntity.ok(courseService.eitherCourse(level, keyword));
    }

    @GetMapping("/above")
    public ResponseEntity<List<Course>> getCourseAboveFee(@RequestParam Double minFee) {
        return ResponseEntity.ok(courseService.getCourseAboveFee(minFee));
    }

    @GetMapping("/native")
    public ResponseEntity<List<Course>> getCourseUpToFee(@RequestParam Double maxFee) {
        return ResponseEntity.ok(courseService.getCourseUpToFee(maxFee));
    }

    @PatchMapping("/status")
    public ResponseEntity<?> updateStatusByLevel(@RequestParam String level, @RequestParam String status) {
        if (status == null || status.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Status is required"));
        }
        return ResponseEntity.ok(Map.of("updated", courseService.updateStatusByLevel(level, status)));
    }

    @GetMapping("/page")
    public ResponseEntity<?> getCoursePage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String direction) {
        if (page < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Page must not be negative"));
        }
        if (size < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Size must be at least 1"));
        }
        Page<Course> result = courseService.getCoursePage(page, size, sortBy, direction);
        return ResponseEntity.ok(result);
    }
}
