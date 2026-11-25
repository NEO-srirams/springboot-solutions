package com.examly.springapp.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Project;
import com.examly.springapp.repository.ProjectRepository;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }
}
