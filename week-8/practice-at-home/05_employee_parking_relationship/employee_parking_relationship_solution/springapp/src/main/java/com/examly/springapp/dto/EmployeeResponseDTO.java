package com.examly.springapp.dto;

public class EmployeeResponseDTO {

    private Long id;
    private String name;
    private String department;
    private Long parkingSlotId;

    public EmployeeResponseDTO() {
    }

    public EmployeeResponseDTO(Long id, String name, String department, Long parkingSlotId) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.parkingSlotId = parkingSlotId;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Long getParkingSlotId() {
        return parkingSlotId;
    }

    public void setParkingSlotId(Long parkingSlotId) {
        this.parkingSlotId = parkingSlotId;
    }
}
