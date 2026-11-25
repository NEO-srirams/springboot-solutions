package com.examly.springapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.Movie;
import com.examly.springapp.service.MovieService;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Movie>> getMovieByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(movieService.getMovieByGenre(genre));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovie(@RequestParam String keyword) {
        return ResponseEntity.ok(movieService.searchMovie(keyword));
    }

    @GetMapping("/rating-range")
    public ResponseEntity<?> getMovieByRatingRange(@RequestParam Double min, @RequestParam Double max) {
        if (min > max) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Minimum must not be greater than maximum"));
        }
        return ResponseEntity.ok(movieService.getMovieByRatingRange(min, max));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Movie>> filterMovie(@RequestParam String genre, @RequestParam Double minRating) {
        return ResponseEntity.ok(movieService.filterMovie(genre, minRating));
    }

    @GetMapping("/either")
    public ResponseEntity<List<Movie>> eitherMovie(@RequestParam String genre, @RequestParam String keyword) {
        return ResponseEntity.ok(movieService.eitherMovie(genre, keyword));
    }

    @GetMapping("/above")
    public ResponseEntity<List<Movie>> getMovieAboveRating(@RequestParam Double minRating) {
        return ResponseEntity.ok(movieService.getMovieAboveRating(minRating));
    }

    @GetMapping("/native")
    public ResponseEntity<List<Movie>> getMovieUpToRating(@RequestParam Double maxRating) {
        return ResponseEntity.ok(movieService.getMovieUpToRating(maxRating));
    }

    @PatchMapping("/status")
    public ResponseEntity<?> updateStatusByGenre(@RequestParam String genre, @RequestParam String status) {
        if (status == null || status.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Status is required"));
        }
        return ResponseEntity.ok(Map.of("updated", movieService.updateStatusByGenre(genre, status)));
    }

    @GetMapping("/page")
    public ResponseEntity<?> getMoviePage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String direction) {
        if (page < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Page must not be negative"));
        }
        if (size < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Size must be at least 1"));
        }
        Page<Movie> result = movieService.getMoviePage(page, size, sortBy, direction);
        return ResponseEntity.ok(result);
    }
}
