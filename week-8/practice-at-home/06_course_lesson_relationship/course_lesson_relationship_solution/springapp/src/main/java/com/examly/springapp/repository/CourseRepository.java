package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
