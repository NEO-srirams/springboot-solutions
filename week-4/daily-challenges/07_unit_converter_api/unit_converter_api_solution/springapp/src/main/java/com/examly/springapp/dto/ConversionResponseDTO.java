package com.examly.springapp.dto;

public class ConversionResponseDTO {

    private String category;
    private double value;
    private String from;
    private String to;
    private double result;

    public ConversionResponseDTO() {
    }

    public ConversionResponseDTO(String category, double value, String from, String to, double result) {
        this.category = category;
        this.value = value;
        this.from = from;
        this.to = to;
        this.result = result;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
}
