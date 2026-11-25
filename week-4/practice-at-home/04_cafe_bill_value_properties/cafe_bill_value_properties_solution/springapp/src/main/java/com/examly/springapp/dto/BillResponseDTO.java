package com.examly.springapp.dto;

public class BillResponseDTO {

    private double amount;
    private double tax;
    private double serviceCharge;
    private double total;
    private String currency;

    public BillResponseDTO() {
    }

    public BillResponseDTO(double amount, double tax, double serviceCharge, double total, String currency) {
        this.amount = amount;
        this.tax = tax;
        this.serviceCharge = serviceCharge;
        this.total = total;
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
