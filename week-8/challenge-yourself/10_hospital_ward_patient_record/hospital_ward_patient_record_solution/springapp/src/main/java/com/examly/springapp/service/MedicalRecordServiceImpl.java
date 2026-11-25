package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.MedicalRecord;
import com.examly.springapp.model.Patient;
import com.examly.springapp.repository.PatientRepository;
import com.examly.springapp.repository.MedicalRecordRepository;

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRecordRepository, PatientRepository patientRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    @Override
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordRepository.findAll();
    }

    @Override
    public Optional<MedicalRecord> getMedicalRecordById(Long id) {
        return medicalRecordRepository.findById(id);
    }

    @Override
    public Optional<MedicalRecord> getMedicalRecordByPatientId(Long patientId) {
        return medicalRecordRepository.findByPatientId(patientId);
    }

    @Override
    public boolean patientHasMedicalRecord(Long patientId) {
        return medicalRecordRepository.existsByPatientId(patientId);
    }

    @Override
    public Optional<MedicalRecord> createMedicalRecordForPatient(Long patientId, MedicalRecord medicalRecord) {
        Optional<Patient> patient = patientRepository.findById(patientId);
        if (patient.isEmpty()) {
            return Optional.empty();
        }
        medicalRecord.setId(null);
        patient.get().setMedicalRecord(medicalRecord);
        return Optional.of(medicalRecordRepository.save(medicalRecord));
    }
}
