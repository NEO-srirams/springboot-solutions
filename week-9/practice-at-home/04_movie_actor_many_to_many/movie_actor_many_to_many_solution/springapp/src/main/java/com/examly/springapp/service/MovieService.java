package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Actor;
import com.examly.springapp.model.Movie;

public interface MovieService {

    Movie saveMovie(Movie movie);

    List<Movie> getAllMovies();

    Optional<Movie> getMovieById(Long id);

    boolean hasActor(Long movieId, Long actorId);

    Movie addActor(Long movieId, Long actorId);

    Movie removeActor(Long movieId, Long actorId);

    void deleteMovie(Long id);
}
