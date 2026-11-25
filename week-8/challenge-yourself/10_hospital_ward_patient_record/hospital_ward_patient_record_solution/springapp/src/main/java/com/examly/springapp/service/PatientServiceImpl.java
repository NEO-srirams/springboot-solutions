package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Patient;
import com.examly.springapp.model.Ward;
import com.examly.springapp.repository.WardRepository;
import com.examly.springapp.repository.PatientRepository;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final WardRepository wardRepository;

    public PatientServiceImpl(PatientRepository patientRepository, WardRepository wardRepository) {
        this.patientRepository = patientRepository;
        this.wardRepository = wardRepository;
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    public List<Patient> getPatientsByWardId(Long wardId) {
        return patientRepository.findByWardId(wardId);
    }

    @Override
    public Optional<Patient> createPatientForWard(Long wardId, Patient patient) {
        Optional<Ward> ward = wardRepository.findById(wardId);
        if (ward.isEmpty()) {
            return Optional.empty();
        }
        patient.setId(null);
        ward.get().addPatient(patient);
        return Optional.of(patientRepository.save(patient));
    }
}
