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
import com.examly.springapp.service.MovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final ActorService actorService;

    public MovieController(MovieService movieService, ActorService actorService) {
        this.movieService = movieService;
        this.actorService = actorService;
    }

    @PostMapping
    public ResponseEntity<?> createMovie(@RequestBody Movie movie) {
        if (movie.getTitle() == null || movie.getTitle().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Title is required"));
        }
        if (movie.getGenre() == null || movie.getGenre().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Genre is required"));
        }
        if (movie.getReleaseYear() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Release year is required"));
        }
        movie.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toMovieResponse(movieService.saveMovie(movie)));
    }

    @GetMapping
    public ResponseEntity<List<MovieResponseDTO>> getAllMovies() {
        List<MovieResponseDTO> result = new ArrayList<>();
        for (Movie x : movieService.getAllMovies()) {
            result.add(toMovieResponse(x));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable Long id) {
        Optional<Movie> movie = movieService.getMovieById(id);
        if (movie.isPresent()) {
            return ResponseEntity.ok(toMovieResponse(movie.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Movie not found with id " + id));
    }

    @PostMapping("/{id}/actors/{actorId}")
    public ResponseEntity<?> addActorToMovie(@PathVariable Long id, @PathVariable Long actorId) {
        if (movieService.getMovieById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Movie not found with id " + id));
        }
        if (actorService.getActorById(actorId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Actor not found with id " + actorId));
        }
        if (movieService.hasActor(id, actorId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Movie already has this actor"));
        }
        return ResponseEntity.ok(toMovieResponse(movieService.addActor(id, actorId)));
    }

    @DeleteMapping("/{id}/actors/{actorId}")
    public ResponseEntity<?> removeActorFromMovie(@PathVariable Long id, @PathVariable Long actorId) {
        if (movieService.getMovieById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Movie not found with id " + id));
        }
        if (actorService.getActorById(actorId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Actor not found with id " + actorId));
        }
        if (!movieService.hasActor(id, actorId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Movie does not have this actor"));
        }
        movieService.removeActor(id, actorId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        if (movieService.getMovieById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Movie not found with id " + id));
        }
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    private MovieResponseDTO toMovieResponse(Movie x) {
        List<ActorSummaryDTO> list = new ArrayList<>();
        for (Actor y : x.getActors()) {
            list.add(new ActorSummaryDTO(y.getId(), y.getName()));
        }
        list.sort(Comparator.comparing(ActorSummaryDTO::getId));
        return new MovieResponseDTO(x.getId(), x.getTitle(), x.getGenre(), x.getReleaseYear(), list);
    }
}
