package com.examly.springapp.dto;

public class DiscountResponseDTO {

    private String type;
    private double originalAmount;
    private double discountPercent;
    private double discountAmount;
    private double finalAmount;

    public DiscountResponseDTO() {
    }

    public DiscountResponseDTO(String type, double originalAmount, double discountPercent, double discountAmount, double finalAmount) {
        this.type = type;
        this.originalAmount = originalAmount;
        this.discountPercent = discountPercent;
        this.discountAmount = discountAmount;
        this.finalAmount = finalAmount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(double originalAmount) {
        this.originalAmount = originalAmount;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }
}
