package com.examly.springapp.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.dto.*;
import com.examly.springapp.model.*;

@RestController
@RequestMapping("/api")
public class HotelController {

    private final HotelInfo hotelInfo;
    private final List<Room> rooms = new ArrayList<>();
    private final List<Booking> bookings = new ArrayList<>();
    private long nextId = 1;

    public HotelController(HotelInfo hotelInfo) {
        this.hotelInfo = hotelInfo;
        rooms.add(new Room(1L, "101", "SINGLE", 2000.0, true));
        rooms.add(new Room(2L, "102", "DOUBLE", 3500.0, true));
        rooms.add(new Room(3L, "201", "SUITE", 8000.0, true));
    }

    @GetMapping("/hotel")
    public ResponseEntity<HotelInfo> getHotelInfo() {
        return ResponseEntity.ok(hotelInfo);
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<Room>> getRooms(@RequestParam(required = false) String type) {
        List<Room> result = new ArrayList<>();
        for (Room r : rooms) {
            if (type == null || r.getType().equalsIgnoreCase(type)) {
                result.add(r);
            }
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable Long id) {
        for (Room r : rooms) {
            if (r.getId().equals(id)) {
                return ResponseEntity.ok(r);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Room not found with id " + id));
    }

    @PostMapping("/bookings")
    public ResponseEntity<?> createBooking(@RequestBody BookingRequestDTO request) {
        if (request.getGuestName() == null || request.getGuestName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Guest name is required"));
        }
        if (request.getNights() < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Nights must be at least 1"));
        }
        if (request.getNights() > hotelInfo.getMaxNights()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Maximum stay is " + hotelInfo.getMaxNights() + " nights"));
        }
        Room room = null;
        for (Room r : rooms) {
            if (r.getId().equals(request.getRoomId())) {
                room = r;
                break;
            }
        }
        if (room == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Room not found with id " + request.getRoomId()));
        }
        if (!room.isAvailable()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Room " + room.getRoomNo() + " is not available"));
        }
        double base = room.getPricePerNight() * request.getNights();
        double tax = base * hotelInfo.getTaxPercent() / 100;
        Booking booking = new Booking(nextId++, room.getId(), request.getGuestName(), request.getNights(), base + tax, "CONFIRMED");
        bookings.add(booking);
        room.setAvailable(false);
        BookingResponseDTO response = new BookingResponseDTO(booking.getId(), room.getRoomNo(), booking.getGuestName(),
                booking.getNights(), booking.getTotalAmount(), booking.getStatus());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<?> getBooking(@PathVariable Long id) {
        for (Booking b : bookings) {
            if (b.getId().equals(id)) {
                String roomNo = null;
                for (Room r : rooms) {
                    if (r.getId().equals(b.getRoomId())) {
                        roomNo = r.getRoomNo();
                    }
                }
                return ResponseEntity.ok(new BookingResponseDTO(b.getId(), roomNo, b.getGuestName(), b.getNights(), b.getTotalAmount(), b.getStatus()));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Booking not found with id " + id));
    }

    @PatchMapping("/bookings/{id}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        Booking booking = null;
        for (Booking b : bookings) {
            if (b.getId().equals(id)) {
                booking = b;
                break;
            }
        }
        if (booking == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Booking not found with id " + id));
        }
        if ("CANCELLED".equals(booking.getStatus())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Booking is already cancelled"));
        }
        booking.setStatus("CANCELLED");
        String roomNo = null;
        for (Room r : rooms) {
            if (r.getId().equals(booking.getRoomId())) {
                r.setAvailable(true);
                roomNo = r.getRoomNo();
            }
        }
        return ResponseEntity.ok(new BookingResponseDTO(booking.getId(), roomNo, booking.getGuestName(), booking.getNights(),
                booking.getTotalAmount(), booking.getStatus()));
    }
}
