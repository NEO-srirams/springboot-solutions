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
import com.examly.springapp.model.CustomerOrder;
import com.examly.springapp.service.CustomerOrderService;

@RestController
@RequestMapping("/api/orders")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    public CustomerOrderController(CustomerOrderService customerOrderService) {
        this.customerOrderService = customerOrderService;
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<CustomerOrder>> getCustomerOrderByCity(@PathVariable String city) {
        return ResponseEntity.ok(customerOrderService.getCustomerOrderByCity(city));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CustomerOrder>> searchCustomerOrder(@RequestParam String keyword) {
        return ResponseEntity.ok(customerOrderService.searchCustomerOrder(keyword));
    }

    @GetMapping("/amount-range")
    public ResponseEntity<?> getCustomerOrderByAmountRange(@RequestParam Double min, @RequestParam Double max) {
        if (min > max) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Minimum must not be greater than maximum"));
        }
        return ResponseEntity.ok(customerOrderService.getCustomerOrderByAmountRange(min, max));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CustomerOrder>> filterCustomerOrder(@RequestParam String city, @RequestParam Double minAmount) {
        return ResponseEntity.ok(customerOrderService.filterCustomerOrder(city, minAmount));
    }

    @GetMapping("/either")
    public ResponseEntity<List<CustomerOrder>> eitherCustomerOrder(@RequestParam String city, @RequestParam String keyword) {
        return ResponseEntity.ok(customerOrderService.eitherCustomerOrder(city, keyword));
    }

    @GetMapping("/above")
    public ResponseEntity<List<CustomerOrder>> getCustomerOrderAboveAmount(@RequestParam Double minAmount) {
        return ResponseEntity.ok(customerOrderService.getCustomerOrderAboveAmount(minAmount));
    }

    @GetMapping("/native")
    public ResponseEntity<List<CustomerOrder>> getCustomerOrderUpToAmount(@RequestParam Double maxAmount) {
        return ResponseEntity.ok(customerOrderService.getCustomerOrderUpToAmount(maxAmount));
    }

    @PatchMapping("/status")
    public ResponseEntity<?> updateStatusByCity(@RequestParam String city, @RequestParam String status) {
        if (status == null || status.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Status is required"));
        }
        return ResponseEntity.ok(Map.of("updated", customerOrderService.updateStatusByCity(city, status)));
    }

    @GetMapping("/page")
    public ResponseEntity<?> getCustomerOrderPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String direction) {
        if (page < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Page must not be negative"));
        }
        if (size < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Size must be at least 1"));
        }
        Page<CustomerOrder> result = customerOrderService.getCustomerOrderPage(page, size, sortBy, direction);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countCustomerOrderByCity(@RequestParam String city) {
        return ResponseEntity.ok(Map.of("count", customerOrderService.countCustomerOrderByCity(city)));
    }

    @GetMapping("/city/{city}/page")
    public ResponseEntity<?> getCustomerOrderByCityPage(@PathVariable String city,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        if (page < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Page must not be negative"));
        }
        if (size < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Size must be at least 1"));
        }
        return ResponseEntity.ok(customerOrderService.getCustomerOrderByCityPage(city, page, size));
    }
}
