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
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final EmployeeService employeeService;
    private final ProjectService projectService;
    private final AssignmentService assignmentService;

    public AssignmentController(EmployeeService employeeService, ProjectService projectService, AssignmentService assignmentService) {
        this.employeeService = employeeService;
        this.projectService = projectService;
        this.assignmentService = assignmentService;
    }

    @PostMapping
    public ResponseEntity<?> createAssignment(@RequestBody AssignmentRequestDTO request) {
        if (request.getEmployeeId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Employee id is required"));
        }
        if (request.getProjectId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Project id is required"));
        }
        if (request.getRole() == null || request.getRole().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Role is required"));
        }
        if (request.getHoursPerWeek() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Hours per week is required"));
        }
        Optional<Employee> employee = employeeService.getEmployeeById(request.getEmployeeId());
        if (employee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Employee not found with id " + request.getEmployeeId()));
        }
        Optional<Project> project = projectService.getProjectById(request.getProjectId());
        if (project.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Project not found with id " + request.getProjectId()));
        }
        if (assignmentService.exists(employee.get(), project.get())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Employee is already assigned to this project"));
        }
        Assignment assignment = new Assignment();
        assignment.setRole(request.getRole());
        assignment.setHoursPerWeek(request.getHoursPerWeek());
        return ResponseEntity.status(HttpStatus.CREATED).body(toAssignmentResponse(assignmentService.saveAssignment(assignment, employee.get(), project.get())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssignment(@PathVariable Long id) {
        if (assignmentService.deleteAssignment(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Assignment not found with id " + id));
    }

    private AssignmentResponseDTO toAssignmentResponse(Assignment x) {
        return new AssignmentResponseDTO(x.getId(), x.getEmployee().getId(), x.getEmployee().getName(), x.getProject().getId(), x.getProject().getName(),
                x.getRole(), x.getHoursPerWeek());
    }
}
