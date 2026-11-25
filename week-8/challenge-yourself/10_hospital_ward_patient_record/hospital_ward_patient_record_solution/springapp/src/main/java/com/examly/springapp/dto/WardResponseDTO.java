package com.examly.springapp.dto;

public class WardResponseDTO {

    private Long id;
    private String name;
    private Integer floor;
    private Integer patientsCount;

    public WardResponseDTO() {
    }

    public WardResponseDTO(Long id, String name, Integer floor, Integer patientsCount) {
        this.id = id;
        this.name = name;
        this.floor = floor;
        this.patientsCount = patientsCount;
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

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getPatientsCount() {
        return patientsCount;
    }

    public void setPatientsCount(Integer patientsCount) {
        this.patientsCount = patientsCount;
    }
}
