package com.examly.springapp.dto;

public class ParkingSlotResponseDTO {

    private Long id;
    private String slotNo;
    private String block;
    private String vehicleNo;
    private Long employeeId;
    private String employeeName;

    public ParkingSlotResponseDTO() {
    }

    public ParkingSlotResponseDTO(Long id, String slotNo, String block, String vehicleNo, Long employeeId, String employeeName) {
        this.id = id;
        this.slotNo = slotNo;
        this.block = block;
        this.vehicleNo = vehicleNo;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(String slotNo) {
        this.slotNo = slotNo;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
