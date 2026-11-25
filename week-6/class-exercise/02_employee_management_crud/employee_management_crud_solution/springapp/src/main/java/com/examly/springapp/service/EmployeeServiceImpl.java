package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.EmployeeRequestDTO;
import com.examly.springapp.model.Employee;
import com.examly.springapp.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
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
    public Optional<Employee> updateEmployee(Long id, EmployeeRequestDTO request) {
        Optional<Employee> existing = employeeRepository.findById(id);
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        Employee employee = existing.get();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());
        employee.setActive(request.getActive());
        return Optional.of(employeeRepository.save(employee));
    }

    @Override
    public boolean deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            return false;
        }
        employeeRepository.deleteById(id);
        return true;
    }
}
