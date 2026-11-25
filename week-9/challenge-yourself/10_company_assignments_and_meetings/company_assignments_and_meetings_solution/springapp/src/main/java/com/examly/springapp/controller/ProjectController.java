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
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final AssignmentService assignmentService;

    public ProjectController(ProjectService projectService, AssignmentService assignmentService) {
        this.projectService = projectService;
        this.assignmentService = assignmentService;
    }

    @PostMapping
    public ResponseEntity<?> createProject(@RequestBody Project project) {
        if (project.getName() == null || project.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (project.getClient() == null || project.getClient().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Client is required"));
        }
        project.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toProjectResponse(projectService.saveProject(project)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectById(@PathVariable Long id) {
        Optional<Project> project = projectService.getProjectById(id);
        if (project.isPresent()) {
            return ResponseEntity.ok(toProjectResponse(project.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Project not found with id " + id));
    }

    @GetMapping("/{id}/assignments")
    public ResponseEntity<?> getAssignmentsByProject(@PathVariable Long id) {
        if (projectService.getProjectById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Project not found with id " + id));
        }
        List<AssignmentResponseDTO> result = new ArrayList<>();
        for (Assignment x : assignmentService.getAssignmentsByProject(id)) {
            result.add(toAssignmentResponse(x));
        }
        return ResponseEntity.ok(result);
    }

    private ProjectResponseDTO toProjectResponse(Project x) {
        return new ProjectResponseDTO(x.getId(), x.getName(), x.getClient());
    }

    private AssignmentResponseDTO toAssignmentResponse(Assignment x) {
        return new AssignmentResponseDTO(x.getId(), x.getEmployee().getId(), x.getEmployee().getName(), x.getProject().getId(), x.getProject().getName(),
                x.getRole(), x.getHoursPerWeek());
    }
}
