package com.examly.springapp.dto;

public class CommentResponseDTO {

    private Long id;
    private String commenter;
    private String text;

    public CommentResponseDTO() {
    }

    public CommentResponseDTO(Long id, String commenter, String text) {
        this.id = id;
        this.commenter = commenter;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
