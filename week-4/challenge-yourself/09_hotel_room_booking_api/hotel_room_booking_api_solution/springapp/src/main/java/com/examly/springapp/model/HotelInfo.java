package com.examly.springapp.model;

public class HotelInfo {

    private String name;
    private int maxNights;
    private double taxPercent;

    public HotelInfo() {
    }

    public HotelInfo(String name, int maxNights, double taxPercent) {
        this.name = name;
        this.maxNights = maxNights;
        this.taxPercent = taxPercent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxNights() {
        return maxNights;
    }

    public void setMaxNights(int maxNights) {
        this.maxNights = maxNights;
    }

    public double getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(double taxPercent) {
        this.taxPercent = taxPercent;
    }
}
