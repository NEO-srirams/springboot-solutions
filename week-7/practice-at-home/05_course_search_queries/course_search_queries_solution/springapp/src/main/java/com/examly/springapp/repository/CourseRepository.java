package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByLevelIgnoreCaseOrderByFeeDesc(String level);

    List<Course> findByTitleContainingIgnoreCase(String keyword);

    List<Course> findByFeeBetween(Double min, Double max);

    List<Course> findByLevelIgnoreCaseAndFeeGreaterThanEqual(String level, Double min);

    List<Course> findByLevelIgnoreCaseOrTitleContainingIgnoreCase(String level, String keyword);

    @Query("SELECT x FROM Course x WHERE x.fee > :min ORDER BY x.fee DESC")
    List<Course> findAboveFee(@Param("min") Double min);

    @Query(value = "SELECT * FROM courses WHERE fee <= :max ORDER BY fee ASC", nativeQuery = true)
    List<Course> findUpToFeeNative(@Param("max") Double max);

    @Modifying
    @Transactional
    @Query("UPDATE Course x SET x.status = :status WHERE LOWER(x.level) = LOWER(:level)")
    int updateStatusByLevel(@Param("level") String level, @Param("status") String status);
}
