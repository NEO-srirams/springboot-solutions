package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.Assignment;
import com.examly.springapp.model.Employee;
import com.examly.springapp.model.Project;
import com.examly.springapp.repository.AssignmentRepository;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public boolean exists(Employee employee, Project project) {
        return assignmentRepository.existsByEmployeeIdAndProjectId(employee.getId(), project.getId());
    }

    @Override
    public Assignment saveAssignment(Assignment assignment, Employee employee, Project project) {
        assignment.setEmployee(employee);
        assignment.setProject(project);
        return assignmentRepository.save(assignment);
    }

    @Transactional
    @Override
    public List<Assignment> getAssignmentsByEmployee(Long employeeId) {
        return assignmentRepository.findByEmployeeWithDetails(employeeId);
    }

    @Transactional
    @Override
    public List<Assignment> getAssignmentsByProject(Long projectId) {
        return assignmentRepository.findByProjectWithDetails(projectId);
    }

    @Override
    public boolean deleteAssignment(Long id) {
        if (!assignmentRepository.existsById(id)) {
            return false;
        }
        assignmentRepository.deleteById(id);
        return true;
    }
}
