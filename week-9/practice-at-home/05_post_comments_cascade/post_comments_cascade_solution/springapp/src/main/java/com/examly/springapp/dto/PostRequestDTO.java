package com.examly.springapp.dto;

import java.util.List;

public class PostRequestDTO {

    private String title;
    private String author;
    private String content;
    private List<CommentRequestDTO> comments;

    public PostRequestDTO() {
    }

    public PostRequestDTO(String title, String author, String content, List<CommentRequestDTO> comments) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.comments = comments;
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

    public List<CommentRequestDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentRequestDTO> comments) {
        this.comments = comments;
    }
}
