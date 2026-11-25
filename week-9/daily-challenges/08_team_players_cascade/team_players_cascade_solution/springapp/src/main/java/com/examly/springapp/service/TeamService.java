package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Player;
import com.examly.springapp.model.Team;

public interface TeamService {

    Team saveTeam(Team team);

    List<Team> getAllTeams();

    Optional<Team> getTeamById(Long id);

    Optional<Player> addPlayer(Long teamId, Player player);

    boolean removePlayer(Long teamId, Long playerId);

    void deleteTeam(Long id);
}
