package com.examly.springapp.dto;

import java.util.List;

public class TeamRequestDTO {

    private String name;
    private String coach;
    private List<PlayerRequestDTO> players;

    public TeamRequestDTO() {
    }

    public TeamRequestDTO(String name, String coach, List<PlayerRequestDTO> players) {
        this.name = name;
        this.coach = coach;
        this.players = players;
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

    public List<PlayerRequestDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerRequestDTO> players) {
        this.players = players;
    }
}
