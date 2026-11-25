package com.examly.springapp.dto;

public class BookingResponseDTO {

    private Long id;
    private String roomNo;
    private String guestName;
    private int nights;
    private double totalAmount;
    private String status;

    public BookingResponseDTO() {
    }

    public BookingResponseDTO(Long id, String roomNo, String guestName, int nights, double totalAmount, String status) {
        this.id = id;
        this.roomNo = roomNo;
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

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
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
