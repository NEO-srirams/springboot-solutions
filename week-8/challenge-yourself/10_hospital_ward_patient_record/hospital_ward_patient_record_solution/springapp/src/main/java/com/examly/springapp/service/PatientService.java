package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Patient;
import com.examly.springapp.model.Ward;

public interface PatientService {

    Patient savePatient(Patient patient);

    List<Patient> getAllPatients();

    Optional<Patient> getPatientById(Long id);

    List<Patient> getPatientsByWardId(Long wardId);

    Optional<Patient> createPatientForWard(Long wardId, Patient patient);
}
