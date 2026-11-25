package com.examly.springapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.Vehicle;
import com.examly.springapp.service.VehicleService;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Vehicle>> getVehicleByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(vehicleService.getVehicleByBrand(brand));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Vehicle>> searchVehicle(@RequestParam String keyword) {
        return ResponseEntity.ok(vehicleService.searchVehicle(keyword));
    }

    @GetMapping("/price-range")
    public ResponseEntity<?> getVehicleByPriceRange(@RequestParam Double min, @RequestParam Double max) {
        if (min > max) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Minimum must not be greater than maximum"));
        }
        return ResponseEntity.ok(vehicleService.getVehicleByPriceRange(min, max));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Vehicle>> filterVehicle(@RequestParam String brand, @RequestParam Double minPrice) {
        return ResponseEntity.ok(vehicleService.filterVehicle(brand, minPrice));
    }

    @GetMapping("/either")
    public ResponseEntity<List<Vehicle>> eitherVehicle(@RequestParam String brand, @RequestParam String keyword) {
        return ResponseEntity.ok(vehicleService.eitherVehicle(brand, keyword));
    }

    @GetMapping("/above")
    public ResponseEntity<List<Vehicle>> getVehicleAbovePrice(@RequestParam Double minPrice) {
        return ResponseEntity.ok(vehicleService.getVehicleAbovePrice(minPrice));
    }

    @GetMapping("/native")
    public ResponseEntity<List<Vehicle>> getVehicleUpToPrice(@RequestParam Double maxPrice) {
        return ResponseEntity.ok(vehicleService.getVehicleUpToPrice(maxPrice));
    }

    @PatchMapping("/status")
    public ResponseEntity<?> updateStatusByBrand(@RequestParam String brand, @RequestParam String status) {
        if (status == null || status.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Status is required"));
        }
        return ResponseEntity.ok(Map.of("updated", vehicleService.updateStatusByBrand(brand, status)));
    }

    @GetMapping("/page")
    public ResponseEntity<?> getVehiclePage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String direction) {
        if (page < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Page must not be negative"));
        }
        if (size < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Size must be at least 1"));
        }
        Page<Vehicle> result = vehicleService.getVehiclePage(page, size, sortBy, direction);
        return ResponseEntity.ok(result);
    }
}
