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
import com.examly.springapp.model.Department;
import com.examly.springapp.model.Employee;
import com.examly.springapp.service.DepartmentService;
import com.examly.springapp.service.EmployeeService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<?> createDepartment(@RequestBody Department department) {
        if (department.getName() == null || department.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (department.getLocation() == null || department.getLocation().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Location is required"));
        }
        department.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(departmentService.saveDepartment(department));
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Long id) {
        Optional<Department> department = departmentService.getDepartmentById(id);
        if (department.isPresent()) {
            return ResponseEntity.ok(department.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Department not found with id " + id));
    }

    @GetMapping("/{id}/employees")
    public ResponseEntity<?> getEmployeesByDepartment(@PathVariable Long id) {
        if (departmentService.getDepartmentById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Department not found with id " + id));
        }
        return ResponseEntity.ok(employeeService.getEmployeesByDepartmentId(id));
    }

    @PostMapping("/{id}/employees")
    public ResponseEntity<?> createEmployeeForDepartment(@PathVariable Long id, @RequestBody Employee employee) {
        if (employee.getName() == null || employee.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (employee.getEmail() == null || employee.getEmail().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Email is required"));
        }
        if (employee.getDesignation() == null || employee.getDesignation().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Designation is required"));
        }
        Optional<Employee> saved = employeeService.createEmployeeForDepartment(id, employee);
        if (saved.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(saved.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Department not found with id " + id));
    }
}
