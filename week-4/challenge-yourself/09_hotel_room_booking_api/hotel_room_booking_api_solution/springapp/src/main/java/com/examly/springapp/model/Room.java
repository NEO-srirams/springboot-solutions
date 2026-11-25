package com.examly.springapp.model;

public class Room {

    private Long id;
    private String roomNo;
    private String type;
    private double pricePerNight;
    private boolean available;

    public Room() {
    }

    public Room(Long id, String roomNo, String type, double pricePerNight, boolean available) {
        this.id = id;
        this.roomNo = roomNo;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.available = available;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
