package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartmentIgnoreCaseOrderBySalaryDesc(String department);

    List<Employee> findByNameContainingIgnoreCase(String keyword);

    List<Employee> findBySalaryBetween(Double min, Double max);

    List<Employee> findByDepartmentIgnoreCaseAndSalaryGreaterThanEqual(String department, Double min);

    List<Employee> findByDepartmentIgnoreCaseOrNameContainingIgnoreCase(String department, String keyword);

    @Query("SELECT x FROM Employee x WHERE x.salary > :min ORDER BY x.salary DESC")
    List<Employee> findAboveSalary(@Param("min") Double min);

    @Query(value = "SELECT * FROM employees WHERE salary <= :max ORDER BY salary ASC", nativeQuery = true)
    List<Employee> findUpToSalaryNative(@Param("max") Double max);

    @Modifying
    @Transactional
    @Query("UPDATE Employee x SET x.status = :status WHERE LOWER(x.department) = LOWER(:department)")
    int updateStatusByDepartment(@Param("department") String department, @Param("status") String status);
}
