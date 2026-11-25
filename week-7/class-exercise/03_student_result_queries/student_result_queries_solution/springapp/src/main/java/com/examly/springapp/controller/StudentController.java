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
import com.examly.springapp.model.Student;
import com.examly.springapp.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Student>> getStudentByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(studentService.getStudentByDepartment(department));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudent(@RequestParam String keyword) {
        return ResponseEntity.ok(studentService.searchStudent(keyword));
    }

    @GetMapping("/marks-range")
    public ResponseEntity<?> getStudentByMarksRange(@RequestParam Integer min, @RequestParam Integer max) {
        if (min > max) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Minimum must not be greater than maximum"));
        }
        return ResponseEntity.ok(studentService.getStudentByMarksRange(min, max));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Student>> filterStudent(@RequestParam String department, @RequestParam Integer minMarks) {
        return ResponseEntity.ok(studentService.filterStudent(department, minMarks));
    }

    @GetMapping("/either")
    public ResponseEntity<List<Student>> eitherStudent(@RequestParam String department, @RequestParam String keyword) {
        return ResponseEntity.ok(studentService.eitherStudent(department, keyword));
    }

    @GetMapping("/above")
    public ResponseEntity<List<Student>> getStudentAboveMarks(@RequestParam Integer minMarks) {
        return ResponseEntity.ok(studentService.getStudentAboveMarks(minMarks));
    }

    @GetMapping("/native")
    public ResponseEntity<List<Student>> getStudentUpToMarks(@RequestParam Integer maxMarks) {
        return ResponseEntity.ok(studentService.getStudentUpToMarks(maxMarks));
    }

    @PatchMapping("/status")
    public ResponseEntity<?> updateStatusByDepartment(@RequestParam String department, @RequestParam String status) {
        if (status == null || status.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Status is required"));
        }
        return ResponseEntity.ok(Map.of("updated", studentService.updateStatusByDepartment(department, status)));
    }

    @GetMapping("/page")
    public ResponseEntity<?> getStudentPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String direction) {
        if (page < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Page must not be negative"));
        }
        if (size < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Size must be at least 1"));
        }
        Page<Student> result = studentService.getStudentPage(page, size, sortBy, direction);
        return ResponseEntity.ok(result);
    }
}
