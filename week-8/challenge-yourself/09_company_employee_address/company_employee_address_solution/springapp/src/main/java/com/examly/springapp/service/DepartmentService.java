package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Department;

public interface DepartmentService {

    Department saveDepartment(Department department);

    List<Department> getAllDepartments();

    Optional<Department> getDepartmentById(Long id);
}
