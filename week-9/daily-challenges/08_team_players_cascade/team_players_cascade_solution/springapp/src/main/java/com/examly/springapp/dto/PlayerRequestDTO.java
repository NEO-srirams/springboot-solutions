package com.examly.springapp.dto;

public class PlayerRequestDTO {

    private String name;
    private Integer jerseyNo;
    private String position;

    public PlayerRequestDTO() {
    }

    public PlayerRequestDTO(String name, Integer jerseyNo, String position) {
        this.name = name;
        this.jerseyNo = jerseyNo;
        this.position = position;
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
