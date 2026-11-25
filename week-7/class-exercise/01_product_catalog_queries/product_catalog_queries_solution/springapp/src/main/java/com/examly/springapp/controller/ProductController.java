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
import com.examly.springapp.model.Product;
import com.examly.springapp.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.getProductByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProduct(keyword));
    }

    @GetMapping("/price-range")
    public ResponseEntity<?> getProductByPriceRange(@RequestParam Double min, @RequestParam Double max) {
        if (min > max) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Minimum must not be greater than maximum"));
        }
        return ResponseEntity.ok(productService.getProductByPriceRange(min, max));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Product>> filterProduct(@RequestParam String category, @RequestParam Double minPrice) {
        return ResponseEntity.ok(productService.filterProduct(category, minPrice));
    }

    @GetMapping("/either")
    public ResponseEntity<List<Product>> eitherProduct(@RequestParam String category, @RequestParam String keyword) {
        return ResponseEntity.ok(productService.eitherProduct(category, keyword));
    }

    @GetMapping("/above")
    public ResponseEntity<List<Product>> getProductAbovePrice(@RequestParam Double minPrice) {
        return ResponseEntity.ok(productService.getProductAbovePrice(minPrice));
    }

    @GetMapping("/native")
    public ResponseEntity<List<Product>> getProductUpToPrice(@RequestParam Double maxPrice) {
        return ResponseEntity.ok(productService.getProductUpToPrice(maxPrice));
    }

    @PatchMapping("/status")
    public ResponseEntity<?> updateStatusByCategory(@RequestParam String category, @RequestParam String status) {
        if (status == null || status.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Status is required"));
        }
        return ResponseEntity.ok(Map.of("updated", productService.updateStatusByCategory(category, status)));
    }

    @GetMapping("/page")
    public ResponseEntity<?> getProductPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String direction) {
        if (page < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Page must not be negative"));
        }
        if (size < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Size must be at least 1"));
        }
        Page<Product> result = productService.getProductPage(page, size, sortBy, direction);
        return ResponseEntity.ok(result);
    }
}
