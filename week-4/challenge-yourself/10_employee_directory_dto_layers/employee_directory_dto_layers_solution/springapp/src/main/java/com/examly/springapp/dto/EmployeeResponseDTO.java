package com.examly.springapp.dto;

public class EmployeeResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String department;
    private String grade;

    public EmployeeResponseDTO() {
    }

    public EmployeeResponseDTO(Long id, String name, String email, String department, String grade) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.grade = grade;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
