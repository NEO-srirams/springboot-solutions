package com.examly.springapp.dto;

import java.util.List;

public class MovieResponseDTO {

    private Long id;
    private String title;
    private String genre;
    private Integer releaseYear;
    private List<ActorSummaryDTO> actors;

    public MovieResponseDTO() {
    }

    public MovieResponseDTO(Long id, String title, String genre, Integer releaseYear, List<ActorSummaryDTO> actors) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.actors = actors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public List<ActorSummaryDTO> getActors() {
        return actors;
    }

    public void setActors(List<ActorSummaryDTO> actors) {
        this.actors = actors;
    }
}
