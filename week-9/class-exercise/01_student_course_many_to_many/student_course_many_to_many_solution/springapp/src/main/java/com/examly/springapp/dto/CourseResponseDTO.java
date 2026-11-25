package com.examly.springapp.dto;

import java.util.List;

public class CourseResponseDTO {

    private Long id;
    private String title;
    private String code;
    private Integer credits;
    private List<StudentSummaryDTO> students;

    public CourseResponseDTO() {
    }

    public CourseResponseDTO(Long id, String title, String code, Integer credits, List<StudentSummaryDTO> students) {
        this.id = id;
        this.title = title;
        this.code = code;
        this.credits = credits;
        this.students = students;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public List<StudentSummaryDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentSummaryDTO> students) {
        this.students = students;
    }
}
