package com.examly.springapp.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.dto.PlayerRequestDTO;
import com.examly.springapp.dto.PlayerResponseDTO;
import com.examly.springapp.dto.TeamRequestDTO;
import com.examly.springapp.dto.TeamResponseDTO;
import com.examly.springapp.model.Player;
import com.examly.springapp.model.Team;
import com.examly.springapp.service.TeamService;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<?> createTeam(@RequestBody TeamRequestDTO request) {
        if (request.getName() == null || request.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (request.getCoach() == null || request.getCoach().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Coach is required"));
        }
        if (request.getPlayers() != null) {
            for (PlayerRequestDTO item : request.getPlayers()) {
            if (item.getName() == null || item.getName().isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDTO(400, "Bad Request", "Player name is required"));
            }
            if (item.getJerseyNo() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDTO(400, "Bad Request", "Player jersey no is required"));
            }
            if (item.getPosition() == null || item.getPosition().isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDTO(400, "Bad Request", "Player position is required"));
            }
            }
        }
        Team team = new Team();
        team.setName(request.getName());
        team.setCoach(request.getCoach());
        if (request.getPlayers() != null) {
            for (PlayerRequestDTO item : request.getPlayers()) {
                Player player = new Player();
                player.setName(item.getName());
                player.setJerseyNo(item.getJerseyNo());
                player.setPosition(item.getPosition());
                team.addPlayer(player);
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(toTeamResponse(teamService.saveTeam(team)));
    }

    @GetMapping
    public ResponseEntity<List<TeamResponseDTO>> getAllTeams() {
        List<TeamResponseDTO> result = new ArrayList<>();
        for (Team x : teamService.getAllTeams()) {
            result.add(toTeamResponse(x));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeamById(@PathVariable Long id) {
        Optional<Team> team = teamService.getTeamById(id);
        if (team.isPresent()) {
            return ResponseEntity.ok(toTeamResponse(team.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Team not found with id " + id));
    }

    @PostMapping("/{id}/players")
    public ResponseEntity<?> addPlayerToTeam(@PathVariable Long id, @RequestBody PlayerRequestDTO item) {
        if (item.getName() == null || item.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (item.getJerseyNo() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Jersey no is required"));
        }
        if (item.getPosition() == null || item.getPosition().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Position is required"));
        }
        Player player = new Player();
        player.setName(item.getName());
        player.setJerseyNo(item.getJerseyNo());
        player.setPosition(item.getPosition());
        Optional<Player> saved = teamService.addPlayer(id, player);
        if (saved.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(toPlayerResponse(saved.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Team not found with id " + id));
    }

    @DeleteMapping("/{id}/players/{playerId}")
    public ResponseEntity<?> removePlayerFromTeam(@PathVariable Long id, @PathVariable Long playerId) {
        if (teamService.getTeamById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Team not found with id " + id));
        }
        if (teamService.removePlayer(id, playerId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Player not found with id " + playerId + " in Team " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long id) {
        if (teamService.getTeamById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Team not found with id " + id));
        }
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }

    private PlayerResponseDTO toPlayerResponse(Player x) {
        return new PlayerResponseDTO(x.getId(), x.getName(), x.getJerseyNo(), x.getPosition());
    }

    private TeamResponseDTO toTeamResponse(Team x) {
        List<PlayerResponseDTO> items = new ArrayList<>();
        for (Player y : x.getPlayers()) {
            items.add(toPlayerResponse(y));
        }
        items.sort(Comparator.comparing(PlayerResponseDTO::getId));
        return new TeamResponseDTO(x.getId(), x.getName(), x.getCoach(), items);
    }
}
