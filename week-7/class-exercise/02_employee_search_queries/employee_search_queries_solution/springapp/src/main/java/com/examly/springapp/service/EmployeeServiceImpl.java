package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Employee;
import com.examly.springapp.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getEmployeeByDepartment(String department) {
        return employeeRepository.findByDepartmentIgnoreCaseOrderBySalaryDesc(department);
    }

    @Override
    public List<Employee> searchEmployee(String keyword) {
        return employeeRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<Employee> getEmployeeBySalaryRange(Double min, Double max) {
        return employeeRepository.findBySalaryBetween(min, max);
    }

    @Override
    public List<Employee> filterEmployee(String department, Double min) {
        return employeeRepository.findByDepartmentIgnoreCaseAndSalaryGreaterThanEqual(department, min);
    }

    @Override
    public List<Employee> eitherEmployee(String department, String keyword) {
        return employeeRepository.findByDepartmentIgnoreCaseOrNameContainingIgnoreCase(department, keyword);
    }

    @Override
    public List<Employee> getEmployeeAboveSalary(Double min) {
        return employeeRepository.findAboveSalary(min);
    }

    @Override
    public List<Employee> getEmployeeUpToSalary(Double max) {
        return employeeRepository.findUpToSalaryNative(max);
    }

    @Override
    public int updateStatusByDepartment(String department, String status) {
        return employeeRepository.updateStatusByDepartment(department, status);
    }

    @Override
    public Page<Employee> getEmployeePage(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return employeeRepository.findAll(PageRequest.of(page, size, sort));
    }
}
