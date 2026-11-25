package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.FacultyRequestDTO;
import com.examly.springapp.model.Faculty;
import com.examly.springapp.repository.FacultyRepository;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty saveFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public List<Faculty> getAllFacultyMembers() {
        return facultyRepository.findAll();
    }

    @Override
    public Optional<Faculty> getFacultyById(Long id) {
        return facultyRepository.findById(id);
    }

    @Override
    public Optional<Faculty> updateFaculty(Long id, FacultyRequestDTO request) {
        Optional<Faculty> existing = facultyRepository.findById(id);
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        Faculty faculty = existing.get();
        faculty.setName(request.getName());
        faculty.setEmail(request.getEmail());
        faculty.setDepartment(request.getDepartment());
        faculty.setActive(request.getActive());
        return Optional.of(facultyRepository.save(faculty));
    }

    @Override
    public boolean deleteFaculty(Long id) {
        if (!facultyRepository.existsById(id)) {
            return false;
        }
        facultyRepository.deleteById(id);
        return true;
    }
}
