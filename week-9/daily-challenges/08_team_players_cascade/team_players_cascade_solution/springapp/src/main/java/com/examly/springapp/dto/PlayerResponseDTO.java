package com.examly.springapp.dto;

public class PlayerResponseDTO {

    private Long id;
    private String name;
    private Integer jerseyNo;
    private String position;

    public PlayerResponseDTO() {
    }

    public PlayerResponseDTO(Long id, String name, Integer jerseyNo, String position) {
        this.id = id;
        this.name = name;
        this.jerseyNo = jerseyNo;
        this.position = position;
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

    public Integer getJerseyNo() {
        return jerseyNo;
    }

    public void setJerseyNo(Integer jerseyNo) {
        this.jerseyNo = jerseyNo;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
