package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.Player;
import com.examly.springapp.model.Team;
import com.examly.springapp.repository.PlayerRepository;
import com.examly.springapp.repository.TeamRepository;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public TeamServiceImpl(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    @Override
    public Team saveTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAllWithPlayers();
    }

    @Override
    public Optional<Team> getTeamById(Long id) {
        return teamRepository.findById(id);
    }

    @Transactional
    @Override
    public Optional<Player> addPlayer(Long teamId, Player player) {
        Optional<Team> team = teamRepository.findById(teamId);
        if (team.isEmpty()) {
            return Optional.empty();
        }
        player.setId(null);
        team.get().addPlayer(player);
        return Optional.of(playerRepository.save(player));
    }

    @Transactional
    @Override
    public boolean removePlayer(Long teamId, Long playerId) {
        Optional<Team> team = teamRepository.findById(teamId);
        if (team.isEmpty()) {
            return false;
        }
        for (Player x : team.get().getPlayers()) {
            if (x.getId().equals(playerId)) {
                team.get().removePlayer(x);
                teamRepository.save(team.get());
                return true;
            }
        }
        return false;
    }

    @Transactional
    @Override
    public void deleteTeam(Long id) {
        teamRepository.deleteById(id);
    }
}
