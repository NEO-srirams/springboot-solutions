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

import com.examly.springapp.dto.AssignmentRequestDTO;
import com.examly.springapp.dto.AssignmentResponseDTO;
import com.examly.springapp.dto.EmployeeResponseDTO;
import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.dto.ProjectResponseDTO;
import com.examly.springapp.model.Assignment;
import com.examly.springapp.model.Employee;
import com.examly.springapp.model.Project;
import com.examly.springapp.service.AssignmentService;
import com.examly.springapp.service.EmployeeService;
import com.examly.springapp.service.ProjectService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AssignmentService assignmentService;

    public EmployeeController(EmployeeService employeeService, AssignmentService assignmentService) {
        this.employeeService = employeeService;
        this.assignmentService = assignmentService;
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        if (employee.getName() == null || employee.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (employee.getDepartment() == null || employee.getDepartment().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Department is required"));
        }
        employee.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toEmployeeResponse(employeeService.saveEmployee(employee)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            return ResponseEntity.ok(toEmployeeResponse(employee.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Employee not found with id " + id));
    }

    @GetMapping("/{id}/assignments")
    public ResponseEntity<?> getAssignmentsByEmployee(@PathVariable Long id) {
        if (employeeService.getEmployeeById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Employee not found with id " + id));
        }
        List<AssignmentResponseDTO> result = new ArrayList<>();
        for (Assignment x : assignmentService.getAssignmentsByEmployee(id)) {
            result.add(toAssignmentResponse(x));
        }
        return ResponseEntity.ok(result);
    }

    private EmployeeResponseDTO toEmployeeResponse(Employee x) {
        return new EmployeeResponseDTO(x.getId(), x.getName(), x.getDepartment());
    }

    private AssignmentResponseDTO toAssignmentResponse(Assignment x) {
        return new AssignmentResponseDTO(x.getId(), x.getEmployee().getId(), x.getEmployee().getName(), x.getProject().getId(), x.getProject().getName(),
                x.getRole(), x.getHoursPerWeek());
    }
}
