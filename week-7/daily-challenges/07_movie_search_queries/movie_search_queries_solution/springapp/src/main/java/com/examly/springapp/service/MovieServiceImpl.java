package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Movie;
import com.examly.springapp.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getMovieByGenre(String genre) {
        return movieRepository.findByGenreIgnoreCaseOrderByRatingDesc(genre);
    }

    @Override
    public List<Movie> searchMovie(String keyword) {
        return movieRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public List<Movie> getMovieByRatingRange(Double min, Double max) {
        return movieRepository.findByRatingBetween(min, max);
    }

    @Override
    public List<Movie> filterMovie(String genre, Double min) {
        return movieRepository.findByGenreIgnoreCaseAndRatingGreaterThanEqual(genre, min);
    }

    @Override
    public List<Movie> eitherMovie(String genre, String keyword) {
        return movieRepository.findByGenreIgnoreCaseOrTitleContainingIgnoreCase(genre, keyword);
    }

    @Override
    public List<Movie> getMovieAboveRating(Double min) {
        return movieRepository.findAboveRating(min);
    }

    @Override
    public List<Movie> getMovieUpToRating(Double max) {
        return movieRepository.findUpToRatingNative(max);
    }

    @Override
    public int updateStatusByGenre(String genre, String status) {
        return movieRepository.updateStatusByGenre(genre, status);
    }

    @Override
    public Page<Movie> getMoviePage(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return movieRepository.findAll(PageRequest.of(page, size, sort));
    }
}
