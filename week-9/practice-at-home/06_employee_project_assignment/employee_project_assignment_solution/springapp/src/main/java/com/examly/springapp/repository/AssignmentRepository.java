package com.examly.springapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    boolean existsByEmployeeIdAndProjectId(Long employeeId, Long projectId);

    @Query("SELECT x FROM Assignment x JOIN FETCH x.employee JOIN FETCH x.project WHERE x.employee.id = :employeeId ORDER BY x.id")
    List<Assignment> findByEmployeeWithDetails(@Param("employeeId") Long employeeId);

    @Query("SELECT x FROM Assignment x JOIN FETCH x.employee JOIN FETCH x.project WHERE x.project.id = :projectId ORDER BY x.id")
    List<Assignment> findByProjectWithDetails(@Param("projectId") Long projectId);
}
