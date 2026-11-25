package com.examly.springapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.FacultyRequestDTO;
import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.Faculty;
import com.examly.springapp.service.FacultyService;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<?> createFaculty(@RequestBody Faculty faculty) {
        if (faculty.getName() == null || faculty.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (faculty.getEmail() == null || faculty.getEmail().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Email is required"));
        }
        if (faculty.getDepartment() == null || faculty.getDepartment().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Department is required"));
        }
        faculty.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(facultyService.saveFaculty(faculty));
    }

    @GetMapping
    public ResponseEntity<List<Faculty>> getAllFacultyMembers() {
        return ResponseEntity.ok(facultyService.getAllFacultyMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFacultyById(@PathVariable Long id) {
        Optional<Faculty> faculty = facultyService.getFacultyById(id);
        if (faculty.isPresent()) {
            return ResponseEntity.ok(faculty.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Faculty not found with id " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFaculty(@PathVariable Long id, @RequestBody FacultyRequestDTO request) {
        if (request.getName() == null || request.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Email is required"));
        }
        if (request.getDepartment() == null || request.getDepartment().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Department is required"));
        }
        Optional<Faculty> updated = facultyService.updateFaculty(id, request);
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Faculty not found with id " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFaculty(@PathVariable Long id) {
        if (facultyService.deleteFaculty(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Faculty not found with id " + id));
    }
}
