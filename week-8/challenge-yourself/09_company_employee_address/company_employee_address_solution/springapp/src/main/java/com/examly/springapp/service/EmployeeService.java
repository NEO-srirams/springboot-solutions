package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Employee;
import com.examly.springapp.model.Department;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(Long id);

    List<Employee> getEmployeesByDepartmentId(Long departmentId);

    Optional<Employee> createEmployeeForDepartment(Long departmentId, Employee employee);
}
