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

@RestController
@RequestMapping("/api/cafe")
public class CafeController {

    @Value("${cafe.name}")
    private String name;

    @Value("${cafe.currency:INR}")
    private String currency;

    @Value("${cafe.tax-percent}")
    private double taxPercent;

    @Value("${cafe.service-charge}")
    private double serviceCharge;

    @Value("${cafe.max-items}")
    private int maxItems;

    @GetMapping("/info")
    public ResponseEntity<CafeInfoDTO> getInfo() {
        return ResponseEntity.ok(new CafeInfoDTO(name, currency, taxPercent, serviceCharge, maxItems));
    }

    @GetMapping("/bill")
    public ResponseEntity<?> getBill(@RequestParam double amount, @RequestParam(defaultValue = "1") int items) {
        if (amount <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Amount must be greater than zero"));
        }
        if (items < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "At least one item is required"));
        }
        if (items > maxItems) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "A maximum of " + maxItems + " items is allowed per order"));
        }
        double tax = amount * taxPercent / 100;
        double total = amount + tax + serviceCharge;
        return ResponseEntity.ok(new BillResponseDTO(amount, tax, serviceCharge, total, currency));
    }
}
