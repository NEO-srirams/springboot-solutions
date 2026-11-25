package com.examly.springapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.PatientResponseDTO;
import com.examly.springapp.dto.MedicalRecordResponseDTO;
import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.Patient;
import com.examly.springapp.model.MedicalRecord;
import com.examly.springapp.service.PatientService;
import com.examly.springapp.service.MedicalRecordService;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;
    private final MedicalRecordService medicalRecordService;

    public PatientController(PatientService patientService, MedicalRecordService medicalRecordService) {
        this.patientService = patientService;
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable Long id) {
        Optional<Patient> patient = patientService.getPatientById(id);
        if (patient.isPresent()) {
            return ResponseEntity.ok(toPatientResponse(patient.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Patient not found with id " + id));
    }

    @GetMapping("/{id}/medical-record")
    public ResponseEntity<?> getMedicalRecordByPatient(@PathVariable Long id) {
        if (patientService.getPatientById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Patient not found with id " + id));
        }
        Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordByPatientId(id);
        if (medicalRecord.isPresent()) {
            return ResponseEntity.ok(toMedicalRecordResponse(medicalRecord.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "No MedicalRecord found for Patient with id " + id));
    }

    @PostMapping("/{id}/medical-record")
    public ResponseEntity<?> createMedicalRecordForPatient(@PathVariable Long id, @RequestBody MedicalRecord medicalRecord) {
        if (medicalRecord.getDiagnosis() == null || medicalRecord.getDiagnosis().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Diagnosis is required"));
        }
        if (medicalRecord.getAdmittedOn() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Admitted on is required"));
        }
        if (patientService.getPatientById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Patient not found with id " + id));
        }
        if (medicalRecordService.patientHasMedicalRecord(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Patient already has a medical record"));
        }
        Optional<MedicalRecord> saved = medicalRecordService.createMedicalRecordForPatient(id, medicalRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(toMedicalRecordResponse(saved.get()));
    }

    private PatientResponseDTO toPatientResponse(Patient x) {
        return new PatientResponseDTO(x.getId(), x.getName(), x.getAge(), x.getBloodGroup(), x.getWard().getId(), x.getWard().getName(), x.getMedicalRecord() == null ? null : x.getMedicalRecord().getId());
    }

    private MedicalRecordResponseDTO toMedicalRecordResponse(MedicalRecord x) {
        return new MedicalRecordResponseDTO(x.getId(), x.getDiagnosis(), x.getAdmittedOn(), x.getTreatment(), x.getPatient().getId(), x.getPatient().getName());
    }
}
