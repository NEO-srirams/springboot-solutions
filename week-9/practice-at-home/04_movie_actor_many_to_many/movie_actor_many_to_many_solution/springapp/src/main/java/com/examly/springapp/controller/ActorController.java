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

import com.examly.springapp.dto.ActorResponseDTO;
import com.examly.springapp.dto.ActorSummaryDTO;
import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.dto.MovieResponseDTO;
import com.examly.springapp.dto.MovieSummaryDTO;
import com.examly.springapp.model.Actor;
import com.examly.springapp.model.Movie;
import com.examly.springapp.service.ActorService;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @PostMapping
    public ResponseEntity<?> createActor(@RequestBody Actor actor) {
        if (actor.getName() == null || actor.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (actor.getCountry() == null || actor.getCountry().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Country is required"));
        }
        actor.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toActorResponse(actorService.saveActor(actor)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getActorById(@PathVariable Long id) {
        Optional<Actor> actor = actorService.getActorById(id);
        if (actor.isPresent()) {
            return ResponseEntity.ok(toActorResponse(actor.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Actor not found with id " + id));
    }

    private ActorResponseDTO toActorResponse(Actor x) {
        List<MovieSummaryDTO> list = new ArrayList<>();
        for (Movie y : x.getMovies()) {
            list.add(new MovieSummaryDTO(y.getId(), y.getTitle()));
        }
        list.sort(Comparator.comparing(MovieSummaryDTO::getId));
        return new ActorResponseDTO(x.getId(), x.getName(), x.getCountry(), list);
    }
}
