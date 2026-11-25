package com.examly.springapp.dto;

public class BookResponseDTO {

    private Long id;
    private String title;
    private String isbn;
    private Double price;
    private Long authorId;
    private String authorName;

    public BookResponseDTO() {
    }

    public BookResponseDTO(Long id, String title, String isbn, Double price, Long authorId, String authorName) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.authorId = authorId;
        this.authorName = authorName;
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

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
