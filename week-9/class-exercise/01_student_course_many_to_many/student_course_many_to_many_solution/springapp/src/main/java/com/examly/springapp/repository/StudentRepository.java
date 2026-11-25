package com.examly.springapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT DISTINCT x FROM Student x LEFT JOIN FETCH x.courses ORDER BY x.id")
    List<Student> findAllWithCourses();
}
