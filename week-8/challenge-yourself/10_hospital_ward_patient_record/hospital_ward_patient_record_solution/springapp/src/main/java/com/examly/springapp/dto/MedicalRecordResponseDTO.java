package com.examly.springapp.dto;

import java.time.LocalDate;

public class MedicalRecordResponseDTO {

    private Long id;
    private String diagnosis;
    private LocalDate admittedOn;
    private String treatment;
    private Long patientId;
    private String patientName;

    public MedicalRecordResponseDTO() {
    }

    public MedicalRecordResponseDTO(Long id, String diagnosis, LocalDate admittedOn, String treatment, Long patientId, String patientName) {
        this.id = id;
        this.diagnosis = diagnosis;
        this.admittedOn = admittedOn;
        this.treatment = treatment;
        this.patientId = patientId;
        this.patientName = patientName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public LocalDate getAdmittedOn() {
        return admittedOn;
    }

    public void setAdmittedOn(LocalDate admittedOn) {
        this.admittedOn = admittedOn;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}
