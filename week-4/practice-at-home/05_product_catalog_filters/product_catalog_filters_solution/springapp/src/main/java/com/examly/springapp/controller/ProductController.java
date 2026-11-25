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
@RequestMapping("/api/products")
public class ProductController {

    private final List<Product> products = new ArrayList<>();

    public ProductController() {
        products.add(new Product(1L, "Wireless Mouse", "Electronics", 599.0, 25));
        products.add(new Product(2L, "Notebook", "Stationery", 60.0, 200));
        products.add(new Product(3L, "USB Cable", "Electronics", 199.0, 80));
        products.add(new Product(4L, "Water Bottle", "Home", 349.0, 40));
        products.add(new Product(5L, "Desk Lamp", "Home", 899.0, 15));
        products.add(new Product(6L, "Gel Pen", "Stationery", 20.0, 500));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String keyword) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (category != null && !p.getCategory().equalsIgnoreCase(category)) {
                continue;
            }
            if (maxPrice != null && p.getPrice() > maxPrice) {
                continue;
            }
            if (keyword != null && !p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                continue;
            }
            result.add(p);
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        if (id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Product id must be positive"));
        }
        for (Product p : products) {
            if (p.getId().equals(id)) {
                return ResponseEntity.ok(p);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Product not found with id " + id));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                result.add(p);
            }
        }
        return ResponseEntity.ok(result);
    }
}
