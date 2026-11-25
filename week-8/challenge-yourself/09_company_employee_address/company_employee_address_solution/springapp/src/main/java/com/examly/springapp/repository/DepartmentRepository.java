package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
