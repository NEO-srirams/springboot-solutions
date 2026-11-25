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

import com.examly.springapp.dto.MedicalRecordResponseDTO;
import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.MedicalRecord;
import com.examly.springapp.service.MedicalRecordService;

@RestController
@RequestMapping("/api/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMedicalRecordById(@PathVariable Long id) {
        Optional<MedicalRecord> medicalRecord = medicalRecordService.getMedicalRecordById(id);
        if (medicalRecord.isPresent()) {
            return ResponseEntity.ok(toMedicalRecordResponse(medicalRecord.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "MedicalRecord not found with id " + id));
    }

    private MedicalRecordResponseDTO toMedicalRecordResponse(MedicalRecord x) {
        return new MedicalRecordResponseDTO(x.getId(), x.getDiagnosis(), x.getAdmittedOn(), x.getTreatment(), x.getPatient().getId(), x.getPatient().getName());
    }
}
