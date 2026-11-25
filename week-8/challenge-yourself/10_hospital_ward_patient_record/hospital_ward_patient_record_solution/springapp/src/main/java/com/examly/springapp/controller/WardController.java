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

import com.examly.springapp.dto.WardResponseDTO;
import com.examly.springapp.dto.PatientResponseDTO;
import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.Ward;
import com.examly.springapp.model.Patient;
import com.examly.springapp.service.WardService;
import com.examly.springapp.service.PatientService;

@RestController
@RequestMapping("/api/wards")
public class WardController {

    private final WardService wardService;
    private final PatientService patientService;

    public WardController(WardService wardService, PatientService patientService) {
        this.wardService = wardService;
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<?> createWard(@RequestBody Ward ward) {
        if (ward.getName() == null || ward.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (ward.getFloor() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Floor is required"));
        }
        ward.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toWardResponse(wardService.saveWard(ward)));
    }

    @GetMapping
    public ResponseEntity<List<WardResponseDTO>> getAllWards() {
        List<WardResponseDTO> result = new ArrayList<>();
        for (Ward x : wardService.getAllWards()) {
            result.add(toWardResponse(x));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWardById(@PathVariable Long id) {
        Optional<Ward> ward = wardService.getWardById(id);
        if (ward.isPresent()) {
            return ResponseEntity.ok(toWardResponse(ward.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Ward not found with id " + id));
    }

    @GetMapping("/{id}/patients")
    public ResponseEntity<?> getPatientsByWard(@PathVariable Long id) {
        if (wardService.getWardById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Ward not found with id " + id));
        }
        List<PatientResponseDTO> result = new ArrayList<>();
        for (Patient x : patientService.getPatientsByWardId(id)) {
            result.add(toPatientResponse(x));
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/patients")
    public ResponseEntity<?> createPatientForWard(@PathVariable Long id, @RequestBody Patient patient) {
        if (patient.getName() == null || patient.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (patient.getAge() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Age is required"));
        }
        if (patient.getBloodGroup() == null || patient.getBloodGroup().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Blood group is required"));
        }
        Optional<Patient> saved = patientService.createPatientForWard(id, patient);
        if (saved.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(toPatientResponse(saved.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Ward not found with id " + id));
    }

    private WardResponseDTO toWardResponse(Ward x) {
        return new WardResponseDTO(x.getId(), x.getName(), x.getFloor(), x.getPatients().size());
    }

    private PatientResponseDTO toPatientResponse(Patient x) {
        return new PatientResponseDTO(x.getId(), x.getName(), x.getAge(), x.getBloodGroup(), x.getWard().getId(), x.getWard().getName(), x.getMedicalRecord() == null ? null : x.getMedicalRecord().getId());
    }
}
