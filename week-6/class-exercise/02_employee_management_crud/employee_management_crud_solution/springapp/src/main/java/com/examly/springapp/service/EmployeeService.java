package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.dto.EmployeeRequestDTO;
import com.examly.springapp.model.Employee;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(Long id);

    Optional<Employee> updateEmployee(Long id, EmployeeRequestDTO request);

    boolean deleteEmployee(Long id);
}
