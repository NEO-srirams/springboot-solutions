package com.examly.springapp.service;

import java.util.Optional;

import com.examly.springapp.model.Employee;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    Optional<Employee> getEmployeeById(Long id);
}
