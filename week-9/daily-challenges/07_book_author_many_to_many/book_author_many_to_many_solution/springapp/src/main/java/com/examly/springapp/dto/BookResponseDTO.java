package com.examly.springapp.dto;

import java.util.List;

public class BookResponseDTO {

    private Long id;
    private String title;
    private String isbn;
    private Double price;
    private List<AuthorSummaryDTO> authors;

    public BookResponseDTO() {
    }

    public BookResponseDTO(Long id, String title, String isbn, Double price, List<AuthorSummaryDTO> authors) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.authors = authors;
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

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<AuthorSummaryDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorSummaryDTO> authors) {
        this.authors = authors;
    }
}
