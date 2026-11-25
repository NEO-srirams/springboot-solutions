package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Doctor;
import com.examly.springapp.repository.DoctorRepository;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<Doctor> getDoctorBySpecialization(String specialization) {
        return doctorRepository.findBySpecializationIgnoreCaseOrderByExperienceYearsDesc(specialization);
    }

    @Override
    public List<Doctor> searchDoctor(String keyword) {
        return doctorRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<Doctor> getDoctorByExperienceYearsRange(Integer min, Integer max) {
        return doctorRepository.findByExperienceYearsBetween(min, max);
    }

    @Override
    public List<Doctor> filterDoctor(String specialization, Integer min) {
        return doctorRepository.findBySpecializationIgnoreCaseAndExperienceYearsGreaterThanEqual(specialization, min);
    }

    @Override
    public List<Doctor> eitherDoctor(String specialization, String keyword) {
        return doctorRepository.findBySpecializationIgnoreCaseOrNameContainingIgnoreCase(specialization, keyword);
    }

    @Override
    public List<Doctor> getDoctorAboveExperienceYears(Integer min) {
        return doctorRepository.findAboveExperienceYears(min);
    }

    @Override
    public List<Doctor> getDoctorUpToExperienceYears(Integer max) {
        return doctorRepository.findUpToExperienceYearsNative(max);
    }

    @Override
    public int updateStatusBySpecialization(String specialization, String status) {
        return doctorRepository.updateStatusBySpecialization(specialization, status);
    }

    @Override
    public Page<Doctor> getDoctorPage(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return doctorRepository.findAll(PageRequest.of(page, size, sort));
    }
}
