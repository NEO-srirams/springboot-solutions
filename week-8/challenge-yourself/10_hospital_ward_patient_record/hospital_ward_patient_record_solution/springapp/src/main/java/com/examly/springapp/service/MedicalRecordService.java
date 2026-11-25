package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.MedicalRecord;
import com.examly.springapp.model.Patient;

public interface MedicalRecordService {

    MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord);

    List<MedicalRecord> getAllMedicalRecords();

    Optional<MedicalRecord> getMedicalRecordById(Long id);

    Optional<MedicalRecord> getMedicalRecordByPatientId(Long patientId);

    boolean patientHasMedicalRecord(Long patientId);

    Optional<MedicalRecord> createMedicalRecordForPatient(Long patientId, MedicalRecord medicalRecord);
}
