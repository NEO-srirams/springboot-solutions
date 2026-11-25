package com.examly.springapp.dto;

import java.util.List;

public class AuthorResponseDTO {

    private Long id;
    private String name;
    private String country;
    private List<BookSummaryDTO> books;

    public AuthorResponseDTO() {
    }

    public AuthorResponseDTO(Long id, String name, String country, List<BookSummaryDTO> books) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.books = books;
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

    public List<BookSummaryDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookSummaryDTO> books) {
        this.books = books;
    }
}
