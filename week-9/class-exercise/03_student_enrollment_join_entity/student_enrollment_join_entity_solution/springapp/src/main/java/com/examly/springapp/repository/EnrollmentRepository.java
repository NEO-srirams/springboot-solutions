package com.examly.springapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    @Query("SELECT x FROM Enrollment x JOIN FETCH x.student JOIN FETCH x.course WHERE x.student.id = :studentId ORDER BY x.id")
    List<Enrollment> findByStudentWithDetails(@Param("studentId") Long studentId);

    @Query("SELECT x FROM Enrollment x JOIN FETCH x.student JOIN FETCH x.course WHERE x.course.id = :courseId ORDER BY x.id")
    List<Enrollment> findByCourseWithDetails(@Param("courseId") Long courseId);
}
