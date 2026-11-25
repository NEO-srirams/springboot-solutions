package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
