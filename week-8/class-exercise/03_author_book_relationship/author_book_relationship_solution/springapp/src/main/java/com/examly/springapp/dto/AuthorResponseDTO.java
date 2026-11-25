package com.examly.springapp.dto;

public class AuthorResponseDTO {

    private Long id;
    private String name;
    private String country;
    private Integer booksCount;

    public AuthorResponseDTO() {
    }

    public AuthorResponseDTO(Long id, String name, String country, Integer booksCount) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.booksCount = booksCount;
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

    public Integer getBooksCount() {
        return booksCount;
    }

    public void setBooksCount(Integer booksCount) {
        this.booksCount = booksCount;
    }
}
