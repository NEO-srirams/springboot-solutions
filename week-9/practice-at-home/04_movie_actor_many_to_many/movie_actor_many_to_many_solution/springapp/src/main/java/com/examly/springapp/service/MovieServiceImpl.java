package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.Actor;
import com.examly.springapp.model.Movie;
import com.examly.springapp.repository.ActorRepository;
import com.examly.springapp.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    public MovieServiceImpl(MovieRepository movieRepository, ActorRepository actorRepository) {
        this.movieRepository = movieRepository;
        this.actorRepository = actorRepository;
    }

    @Override
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAllWithActors();
    }

    @Override
    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    @Transactional
    @Override
    public boolean hasActor(Long movieId, Long actorId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        return movie.getActors().stream().anyMatch(x -> x.getId().equals(actorId));
    }

    @Transactional
    @Override
    public Movie addActor(Long movieId, Long actorId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        Actor actor = actorRepository.findById(actorId).orElseThrow();
        movie.addActor(actor);
        return movieRepository.save(movie);
    }

    @Transactional
    @Override
    public Movie removeActor(Long movieId, Long actorId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow();
        Actor actor = actorRepository.findById(actorId).orElseThrow();
        movie.removeActor(actor);
        return movieRepository.save(movie);
    }

    @Transactional
    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}
