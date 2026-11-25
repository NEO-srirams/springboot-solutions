package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.dto.FacultyRequestDTO;
import com.examly.springapp.model.Faculty;

public interface FacultyService {

    Faculty saveFaculty(Faculty faculty);

    List<Faculty> getAllFacultyMembers();

    Optional<Faculty> getFacultyById(Long id);

    Optional<Faculty> updateFaculty(Long id, FacultyRequestDTO request);

    boolean deleteFaculty(Long id);
}
