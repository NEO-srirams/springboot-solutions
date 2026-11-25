package com.examly.springapp.dto;

public class StudentSummaryDTO {

    private Long id;
    private String name;

    public StudentSummaryDTO() {
    }

    public StudentSummaryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
