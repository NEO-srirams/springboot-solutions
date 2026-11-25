package com.examly.springapp.dto;

public class BookingRequestDTO {

    private Long roomId;
    private String guestName;
    private int nights;

    public BookingRequestDTO() {
    }

    public BookingRequestDTO(Long roomId, String guestName, int nights) {
        this.roomId = roomId;
        this.guestName = guestName;
        this.nights = nights;
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
}
