package com.examly.springapp.dto;

public class FeedbackSummaryDTO {

    private int total;
    private double averageRating;

    public FeedbackSummaryDTO() {
    }

    public FeedbackSummaryDTO(int total, double averageRating) {
        this.total = total;
        this.averageRating = averageRating;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}
