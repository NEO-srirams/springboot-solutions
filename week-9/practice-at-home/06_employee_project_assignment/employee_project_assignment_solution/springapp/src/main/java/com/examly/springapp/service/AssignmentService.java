package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Assignment;
import com.examly.springapp.model.Employee;
import com.examly.springapp.model.Project;

public interface AssignmentService {

    boolean exists(Employee employee, Project project);

    Assignment saveAssignment(Assignment assignment, Employee employee, Project project);

    List<Assignment> getAssignmentsByEmployee(Long employeeId);

    List<Assignment> getAssignmentsByProject(Long projectId);

    boolean deleteAssignment(Long id);
}
