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
import com.examly.springapp.service.StudentService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final CourseService courseService;

    public StudentController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        if (student.getName() == null || student.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (student.getEmail() == null || student.getEmail().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Email is required"));
        }
        if (student.getDepartment() == null || student.getDepartment().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Department is required"));
        }
        student.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toStudentResponse(studentService.saveStudent(student)));
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<StudentResponseDTO> result = new ArrayList<>();
        for (Student x : studentService.getAllStudents()) {
            result.add(toStudentResponse(x));
        }
        return ResponseEntity.ok(result);
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

    @PostMapping("/{id}/courses/{courseId}")
    public ResponseEntity<?> addCourseToStudent(@PathVariable Long id, @PathVariable Long courseId) {
        if (studentService.getStudentById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Student not found with id " + id));
        }
        if (courseService.getCourseById(courseId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Course not found with id " + courseId));
        }
        if (studentService.hasCourse(id, courseId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Student is already enrolled in this course"));
        }
        return ResponseEntity.ok(toStudentResponse(studentService.addCourse(id, courseId)));
    }

    @DeleteMapping("/{id}/courses/{courseId}")
    public ResponseEntity<?> removeCourseFromStudent(@PathVariable Long id, @PathVariable Long courseId) {
        if (studentService.getStudentById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Student not found with id " + id));
        }
        if (courseService.getCourseById(courseId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Course not found with id " + courseId));
        }
        if (!studentService.hasCourse(id, courseId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Student is not enrolled in this course"));
        }
        studentService.removeCourse(id, courseId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        if (studentService.getStudentById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Student not found with id " + id));
        }
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    private StudentResponseDTO toStudentResponse(Student x) {
        List<CourseSummaryDTO> list = new ArrayList<>();
        for (Course y : x.getCourses()) {
            list.add(new CourseSummaryDTO(y.getId(), y.getTitle()));
        }
        list.sort(Comparator.comparing(CourseSummaryDTO::getId));
        return new StudentResponseDTO(x.getId(), x.getName(), x.getEmail(), x.getDepartment(), list);
    }
}
