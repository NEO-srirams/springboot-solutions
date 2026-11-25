package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.examly.springapp.model.Doctor;

public interface DoctorService {

    List<Doctor> getDoctorBySpecialization(String specialization);

    List<Doctor> searchDoctor(String keyword);

    List<Doctor> getDoctorByExperienceYearsRange(Integer min, Integer max);

    List<Doctor> filterDoctor(String specialization, Integer min);

    List<Doctor> eitherDoctor(String specialization, String keyword);

    List<Doctor> getDoctorAboveExperienceYears(Integer min);

    List<Doctor> getDoctorUpToExperienceYears(Integer max);

    int updateStatusBySpecialization(String specialization, String status);

    Page<Doctor> getDoctorPage(int page, int size, String sortBy, String direction);
}
