package com.examly.springapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.CustomerOrderRequestDTO;
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

    @PostMapping
    public ResponseEntity<?> createCustomerOrder(@RequestBody CustomerOrder customerOrder) {
        if (customerOrder.getOrderNo() == null || customerOrder.getOrderNo().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Order no is required"));
        }
        if (customerOrder.getCustomerName() == null || customerOrder.getCustomerName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Customer name is required"));
        }
        if (customerOrder.getAmount() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Amount is required"));
        }
        customerOrder.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerOrderService.saveCustomerOrder(customerOrder));
    }

    @GetMapping
    public ResponseEntity<List<CustomerOrder>> getAllOrders() {
        return ResponseEntity.ok(customerOrderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerOrderById(@PathVariable Long id) {
        Optional<CustomerOrder> customerOrder = customerOrderService.getCustomerOrderById(id);
        if (customerOrder.isPresent()) {
            return ResponseEntity.ok(customerOrder.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "CustomerOrder not found with id " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomerOrder(@PathVariable Long id, @RequestBody CustomerOrderRequestDTO request) {
        if (request.getCustomerName() == null || request.getCustomerName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Customer name is required"));
        }
        if (request.getAmount() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Amount is required"));
        }
        Optional<CustomerOrder> updated = customerOrderService.updateCustomerOrder(id, request);
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "CustomerOrder not found with id " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerOrder(@PathVariable Long id) {
        if (customerOrderService.deleteCustomerOrder(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "CustomerOrder not found with id " + id));
    }
}
