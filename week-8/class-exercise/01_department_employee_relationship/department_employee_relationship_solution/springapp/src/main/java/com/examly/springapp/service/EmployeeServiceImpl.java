package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Employee;
import com.examly.springapp.model.Department;
import com.examly.springapp.repository.DepartmentRepository;
import com.examly.springapp.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> getEmployeesByDepartmentId(Long departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

    @Override
    public Optional<Employee> createEmployeeForDepartment(Long departmentId, Employee employee) {
        Optional<Department> department = departmentRepository.findById(departmentId);
        if (department.isEmpty()) {
            return Optional.empty();
        }
        employee.setId(null);
        department.get().addEmployee(employee);
        return Optional.of(employeeRepository.save(employee));
    }
}
