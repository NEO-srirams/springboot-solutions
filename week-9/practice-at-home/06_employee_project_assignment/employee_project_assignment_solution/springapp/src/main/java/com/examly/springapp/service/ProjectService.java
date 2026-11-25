package com.examly.springapp.service;

import java.util.Optional;

import com.examly.springapp.model.Project;

public interface ProjectService {

    Project saveProject(Project project);

    Optional<Project> getProjectById(Long id);
}
