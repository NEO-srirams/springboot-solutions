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
@RequestMapping("/api/discounts")
public class DiscountController {

    private final List<DiscountPolicy> policies;

    public DiscountController(List<DiscountPolicy> policies) {
        this.policies = policies;
    }

    @GetMapping
    public ResponseEntity<List<DiscountPolicy>> getPolicies() {
        return ResponseEntity.ok(policies);
    }

    @GetMapping("/price")
    public ResponseEntity<?> getDiscountedPrice(@RequestParam String type, @RequestParam double amount) {
        if (amount <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Amount must be greater than zero"));
        }
        for (DiscountPolicy p : policies) {
            if (p.getName().equalsIgnoreCase(type)) {
                double discount = amount * p.getPercentage() / 100;
                return ResponseEntity.ok(new DiscountResponseDTO(p.getName(), amount, p.getPercentage(), discount, amount - discount));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Discount policy not found for type " + type));
    }
}
