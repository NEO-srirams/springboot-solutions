package com.examly.springapp.model;

public class Booking {

    private Long id;
    private Long roomId;
    private String guestName;
    private int nights;
    private double totalAmount;
    private String status;

    public Booking() {
    }

    public Booking(Long id, Long roomId, String guestName, int nights, double totalAmount, String status) {
        this.id = id;
        this.roomId = roomId;
        this.guestName = guestName;
        this.nights = nights;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public int getNights() {
        return nights;
    }

    public void setNights(int nights) {
        this.nights = nights;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
