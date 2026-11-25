package com.examly.springapp.dto;

import java.util.List;

public class PostResponseDTO {

    private Long id;
    private String title;
    private String author;
    private String content;
    private List<CommentResponseDTO> comments;

    public PostResponseDTO() {
    }

    public PostResponseDTO(Long id, String title, String author, String content, List<CommentResponseDTO> comments) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.content = content;
        this.comments = comments;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<CommentResponseDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentResponseDTO> comments) {
        this.comments = comments;
    }
}
