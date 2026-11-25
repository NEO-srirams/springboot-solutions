package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.examly.springapp.model.Movie;

public interface MovieService {

    List<Movie> getMovieByGenre(String genre);

    List<Movie> searchMovie(String keyword);

    List<Movie> getMovieByRatingRange(Double min, Double max);

    List<Movie> filterMovie(String genre, Double min);

    List<Movie> eitherMovie(String genre, String keyword);

    List<Movie> getMovieAboveRating(Double min);

    List<Movie> getMovieUpToRating(Double max);

    int updateStatusByGenre(String genre, String status);

    Page<Movie> getMoviePage(int page, int size, String sortBy, String direction);
}
