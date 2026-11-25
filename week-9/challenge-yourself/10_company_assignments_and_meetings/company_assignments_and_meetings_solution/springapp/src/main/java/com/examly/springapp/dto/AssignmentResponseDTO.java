package com.examly.springapp.dto;

public class AssignmentResponseDTO {

    private Long id;
    private Long employeeId;
    private String employeeName;
    private Long projectId;
    private String projectName;
    private String role;
    private Integer hoursPerWeek;

    public AssignmentResponseDTO() {
    }

    public AssignmentResponseDTO(Long id, Long employeeId, String employeeName, Long projectId, String projectName, String role, Integer hoursPerWeek) {
        this.id = id;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.projectId = projectId;
        this.projectName = projectName;
        this.role = role;
        this.hoursPerWeek = hoursPerWeek;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
