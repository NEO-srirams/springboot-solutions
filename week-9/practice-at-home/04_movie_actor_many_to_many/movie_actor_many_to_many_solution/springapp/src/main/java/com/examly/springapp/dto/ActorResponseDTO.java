package com.examly.springapp.dto;

import java.util.List;

public class ActorResponseDTO {

    private Long id;
    private String name;
    private String country;
    private List<MovieSummaryDTO> movies;

    public ActorResponseDTO() {
    }

    public ActorResponseDTO(Long id, String name, String country, List<MovieSummaryDTO> movies) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.movies = movies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<MovieSummaryDTO> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieSummaryDTO> movies) {
        this.movies = movies;
    }
}
