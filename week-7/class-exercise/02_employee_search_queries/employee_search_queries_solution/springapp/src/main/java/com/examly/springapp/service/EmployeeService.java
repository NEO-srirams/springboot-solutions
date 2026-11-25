package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.examly.springapp.model.Employee;

public interface EmployeeService {

    List<Employee> getEmployeeByDepartment(String department);

    List<Employee> searchEmployee(String keyword);

    List<Employee> getEmployeeBySalaryRange(Double min, Double max);

    List<Employee> filterEmployee(String department, Double min);

    List<Employee> eitherEmployee(String department, String keyword);

    List<Employee> getEmployeeAboveSalary(Double min);

    List<Employee> getEmployeeUpToSalary(Double max);

    int updateStatusByDepartment(String department, String status);

    Page<Employee> getEmployeePage(int page, int size, String sortBy, String direction);
}
