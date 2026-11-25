package com.examly.springapp.dto;

import java.util.List;

public class StudentResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String department;
    private List<CourseSummaryDTO> courses;

    public StudentResponseDTO() {
    }

    public StudentResponseDTO(Long id, String name, String email, String department, List<CourseSummaryDTO> courses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.courses = courses;
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

    public List<CourseSummaryDTO> getCourses() {
        return courses;
    }

    public void setCourses(List<CourseSummaryDTO> courses) {
        this.courses = courses;
    }
}
