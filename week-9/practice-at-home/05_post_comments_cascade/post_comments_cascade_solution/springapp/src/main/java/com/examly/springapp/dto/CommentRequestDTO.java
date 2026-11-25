package com.examly.springapp.dto;

public class CommentRequestDTO {

    private String commenter;
    private String text;

    public CommentRequestDTO() {
    }

    public CommentRequestDTO(String commenter, String text) {
        this.commenter = commenter;
        this.text = text;
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
