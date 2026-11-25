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
import com.examly.springapp.model.Student;
import com.examly.springapp.model.IdCard;
import com.examly.springapp.service.StudentService;
import com.examly.springapp.service.IdCardService;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final IdCardService idCardService;

    public StudentController(StudentService studentService, IdCardService idCardService) {
        this.studentService = studentService;
        this.idCardService = idCardService;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.saveStudent(student));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Student not found with id " + id));
    }

    @GetMapping("/{id}/id-card")
    public ResponseEntity<?> getIdCardByStudent(@PathVariable Long id) {
        if (studentService.getStudentById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Student not found with id " + id));
        }
        Optional<IdCard> idCard = idCardService.getIdCardByStudentId(id);
        if (idCard.isPresent()) {
            return ResponseEntity.ok(idCard.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "No IdCard found for Student with id " + id));
    }

    @PostMapping("/{id}/id-card")
    public ResponseEntity<?> createIdCardForStudent(@PathVariable Long id, @RequestBody IdCard idCard) {
        if (idCard.getCardNo() == null || idCard.getCardNo().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Card no is required"));
        }
        if (idCard.getIssuedOn() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Issued on is required"));
        }
        if (idCard.getValidTill() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Valid till is required"));
        }
        if (studentService.getStudentById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Student not found with id " + id));
        }
        if (idCardService.studentHasIdCard(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Student already has an id card"));
        }
        Optional<IdCard> saved = idCardService.createIdCardForStudent(id, idCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved.get());
    }
}
