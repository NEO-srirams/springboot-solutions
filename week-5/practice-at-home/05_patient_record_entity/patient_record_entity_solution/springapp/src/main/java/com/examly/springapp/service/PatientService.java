package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Patient;

public interface PatientService {

    Patient savePatient(Patient patient);

    List<Patient> getAllPatients();

    Optional<Patient> getPatientById(Long id);
}
