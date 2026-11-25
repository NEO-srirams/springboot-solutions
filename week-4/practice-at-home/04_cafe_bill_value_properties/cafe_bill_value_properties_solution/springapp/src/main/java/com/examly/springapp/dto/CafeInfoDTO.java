package com.examly.springapp.dto;

public class CafeInfoDTO {

    private String name;
    private String currency;
    private double taxPercent;
    private double serviceCharge;
    private int maxItemsPerOrder;

    public CafeInfoDTO() {
    }

    public CafeInfoDTO(String name, String currency, double taxPercent, double serviceCharge, int maxItemsPerOrder) {
        this.name = name;
        this.currency = currency;
        this.taxPercent = taxPercent;
        this.serviceCharge = serviceCharge;
        this.maxItemsPerOrder = maxItemsPerOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getTaxPercent() {
        return taxPercent;
    }

    public void setTaxPercent(double taxPercent) {
        this.taxPercent = taxPercent;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public int getMaxItemsPerOrder() {
        return maxItemsPerOrder;
    }

    public void setMaxItemsPerOrder(int maxItemsPerOrder) {
        this.maxItemsPerOrder = maxItemsPerOrder;
    }
}
