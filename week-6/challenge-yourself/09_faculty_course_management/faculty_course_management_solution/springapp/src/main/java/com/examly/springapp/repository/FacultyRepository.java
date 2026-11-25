package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
