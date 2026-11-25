package com.examly.springapp.dto;

import java.util.List;

public class TeamResponseDTO {

    private Long id;
    private String name;
    private String coach;
    private List<PlayerResponseDTO> players;

    public TeamResponseDTO() {
    }

    public TeamResponseDTO(Long id, String name, String coach, List<PlayerResponseDTO> players) {
        this.id = id;
        this.name = name;
        this.coach = coach;
        this.players = players;
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

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public List<PlayerResponseDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerResponseDTO> players) {
        this.players = players;
    }
}
