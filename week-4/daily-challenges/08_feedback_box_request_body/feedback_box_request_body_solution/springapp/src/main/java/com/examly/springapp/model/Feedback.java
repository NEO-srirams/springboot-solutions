package com.examly.springapp.model;

public class Feedback {

    private Long id;
    private String studentName;
    private String course;
    private int rating;
    private String comments;
    private String status;

    public Feedback() {
    }

    public Feedback(Long id, String studentName, String course, int rating, String comments, String status) {
        this.id = id;
        this.studentName = studentName;
        this.course = course;
        this.rating = rating;
        this.comments = comments;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
