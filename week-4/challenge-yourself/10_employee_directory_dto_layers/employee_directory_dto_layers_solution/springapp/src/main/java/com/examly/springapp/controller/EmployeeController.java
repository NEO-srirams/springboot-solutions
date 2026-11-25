package com.examly.springapp.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.dto.*;
import com.examly.springapp.model.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final List<Employee> employees = new ArrayList<>();
    private long nextId = 4;

    public EmployeeController() {
        employees.add(new Employee(1L, "Arun Kumar", "arun@college.edu", "Engineering", 85000.0));
        employees.add(new Employee(2L, "Meena Iyer", "meena@college.edu", "Mathematics", 55000.0));
        employees.add(new Employee(3L, "Ravi Shankar", "ravi@college.edu", "Admin", 30000.0));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees(@RequestParam(required = false) String department) {
        List<EmployeeResponseDTO> result = new ArrayList<>();
        for (Employee e : employees) {
            if (department == null || e.getDepartment().equalsIgnoreCase(department)) {
                result.add(toResponse(e));
            }
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        for (Employee e : employees) {
            if (e.getId().equals(id)) {
                return ResponseEntity.ok(toResponse(e));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Employee not found with id " + id));
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeRequestDTO request) {
        if (request.getName() == null || request.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (request.getEmail() == null || !request.getEmail().contains("@")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Invalid email address"));
        }
        if (request.getSalary() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Salary must be greater than zero"));
        }
        for (Employee e : employees) {
            if (e.getEmail().equalsIgnoreCase(request.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDTO(400, "Bad Request", "Employee already exists with email " + request.getEmail()));
            }
        }
        Employee employee = new Employee(nextId++, request.getName(), request.getEmail(), request.getDepartment(), request.getSalary());
        employees.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody EmployeeRequestDTO request) {
        Employee employee = null;
        for (Employee e : employees) {
            if (e.getId().equals(id)) {
                employee = e;
                break;
            }
        }
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Employee not found with id " + id));
        }
        if (request.getName() == null || request.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (request.getEmail() == null || !request.getEmail().contains("@")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Invalid email address"));
        }
        if (request.getSalary() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Salary must be greater than zero"));
        }
        for (Employee e : employees) {
            if (e.getEmail().equalsIgnoreCase(request.getEmail()) && !e.getId().equals(id)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDTO(400, "Bad Request", "Employee already exists with email " + request.getEmail()));
            }
        }
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());
        return ResponseEntity.ok(toResponse(employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        for (Employee e : employees) {
            if (e.getId().equals(id)) {
                employees.remove(e);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Employee not found with id " + id));
    }

    private EmployeeResponseDTO toResponse(Employee e) {
        String grade = e.getSalary() >= 80000 ? "A" : e.getSalary() >= 50000 ? "B" : "C";
        return new EmployeeResponseDTO(e.getId(), e.getName(), e.getEmail(), e.getDepartment(), grade);
    }
}
