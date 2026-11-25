package com.examly.springapp.dto;

public class AssignmentRequestDTO {

    private Long employeeId;
    private Long projectId;
    private String role;
    private Integer hoursPerWeek;

    public AssignmentRequestDTO() {
    }

    public AssignmentRequestDTO(Long employeeId, Long projectId, String role, Integer hoursPerWeek) {
        this.employeeId = employeeId;
        this.projectId = projectId;
        this.role = role;
        this.hoursPerWeek = hoursPerWeek;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(Integer hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }
}
