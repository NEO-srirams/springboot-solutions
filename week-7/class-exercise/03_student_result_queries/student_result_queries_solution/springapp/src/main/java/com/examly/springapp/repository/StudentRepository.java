package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByDepartmentIgnoreCaseOrderByMarksDesc(String department);

    List<Student> findByNameContainingIgnoreCase(String keyword);

    List<Student> findByMarksBetween(Integer min, Integer max);

    List<Student> findByDepartmentIgnoreCaseAndMarksGreaterThanEqual(String department, Integer min);

    List<Student> findByDepartmentIgnoreCaseOrNameContainingIgnoreCase(String department, String keyword);

    @Query("SELECT x FROM Student x WHERE x.marks > :min ORDER BY x.marks DESC")
    List<Student> findAboveMarks(@Param("min") Integer min);

    @Query(value = "SELECT * FROM students WHERE marks <= :max ORDER BY marks ASC", nativeQuery = true)
    List<Student> findUpToMarksNative(@Param("max") Integer max);

    @Modifying
    @Transactional
    @Query("UPDATE Student x SET x.status = :status WHERE LOWER(x.department) = LOWER(:department)")
    int updateStatusByDepartment(@Param("department") String department, @Param("status") String status);
}
