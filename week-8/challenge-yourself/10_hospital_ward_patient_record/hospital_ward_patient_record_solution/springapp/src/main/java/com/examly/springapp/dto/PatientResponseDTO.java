package com.examly.springapp.dto;

public class PatientResponseDTO {

    private Long id;
    private String name;
    private Integer age;
    private String bloodGroup;
    private Long wardId;
    private String wardName;
    private Long medicalRecordId;

    public PatientResponseDTO() {
    }

    public PatientResponseDTO(Long id, String name, Integer age, String bloodGroup, Long wardId, String wardName, Long medicalRecordId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.wardId = wardId;
        this.wardName = wardName;
        this.medicalRecordId = medicalRecordId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Long getWardId() {
        return wardId;
    }

    public void setWardId(Long wardId) {
        this.wardId = wardId;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public Long getMedicalRecordId() {
        return medicalRecordId;
    }

    public void setMedicalRecordId(Long medicalRecordId) {
        this.medicalRecordId = medicalRecordId;
    }
}
