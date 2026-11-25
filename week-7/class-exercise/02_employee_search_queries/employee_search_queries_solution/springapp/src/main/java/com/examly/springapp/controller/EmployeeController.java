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
import com.examly.springapp.model.Employee;
import com.examly.springapp.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Employee>> getEmployeeByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(employeeService.getEmployeeByDepartment(department));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployee(@RequestParam String keyword) {
        return ResponseEntity.ok(employeeService.searchEmployee(keyword));
    }

    @GetMapping("/salary-range")
    public ResponseEntity<?> getEmployeeBySalaryRange(@RequestParam Double min, @RequestParam Double max) {
        if (min > max) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Minimum must not be greater than maximum"));
        }
        return ResponseEntity.ok(employeeService.getEmployeeBySalaryRange(min, max));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Employee>> filterEmployee(@RequestParam String department, @RequestParam Double minSalary) {
        return ResponseEntity.ok(employeeService.filterEmployee(department, minSalary));
    }

    @GetMapping("/either")
    public ResponseEntity<List<Employee>> eitherEmployee(@RequestParam String department, @RequestParam String keyword) {
        return ResponseEntity.ok(employeeService.eitherEmployee(department, keyword));
    }

    @GetMapping("/above")
    public ResponseEntity<List<Employee>> getEmployeeAboveSalary(@RequestParam Double minSalary) {
        return ResponseEntity.ok(employeeService.getEmployeeAboveSalary(minSalary));
    }

    @GetMapping("/native")
    public ResponseEntity<List<Employee>> getEmployeeUpToSalary(@RequestParam Double maxSalary) {
        return ResponseEntity.ok(employeeService.getEmployeeUpToSalary(maxSalary));
    }

    @PatchMapping("/status")
    public ResponseEntity<?> updateStatusByDepartment(@RequestParam String department, @RequestParam String status) {
        if (status == null || status.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Status is required"));
        }
        return ResponseEntity.ok(Map.of("updated", employeeService.updateStatusByDepartment(department, status)));
    }

    @GetMapping("/page")
    public ResponseEntity<?> getEmployeePage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String direction) {
        if (page < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Page must not be negative"));
        }
        if (size < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Size must be at least 1"));
        }
        Page<Employee> result = employeeService.getEmployeePage(page, size, sortBy, direction);
        return ResponseEntity.ok(result);
    }
}
