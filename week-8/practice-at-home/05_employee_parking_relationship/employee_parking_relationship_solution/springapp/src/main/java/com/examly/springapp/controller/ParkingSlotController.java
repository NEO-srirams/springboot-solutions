package com.examly.springapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.ParkingSlotResponseDTO;
import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.ParkingSlot;
import com.examly.springapp.service.ParkingSlotService;

@RestController
@RequestMapping("/api/parking-slots")
public class ParkingSlotController {

    private final ParkingSlotService parkingSlotService;

    public ParkingSlotController(ParkingSlotService parkingSlotService) {
        this.parkingSlotService = parkingSlotService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getParkingSlotById(@PathVariable Long id) {
        Optional<ParkingSlot> parkingSlot = parkingSlotService.getParkingSlotById(id);
        if (parkingSlot.isPresent()) {
            return ResponseEntity.ok(toParkingSlotResponse(parkingSlot.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "ParkingSlot not found with id " + id));
    }

    private ParkingSlotResponseDTO toParkingSlotResponse(ParkingSlot x) {
        return new ParkingSlotResponseDTO(x.getId(), x.getSlotNo(), x.getBlock(), x.getVehicleNo(), x.getEmployee().getId(), x.getEmployee().getName());
    }
}
