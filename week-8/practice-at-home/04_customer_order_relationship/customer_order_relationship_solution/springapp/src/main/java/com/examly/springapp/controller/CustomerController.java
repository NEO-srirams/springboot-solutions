package com.examly.springapp.controller;

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

import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.Customer;
import com.examly.springapp.model.CustomerOrder;
import com.examly.springapp.service.CustomerService;
import com.examly.springapp.service.CustomerOrderService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerOrderService customerOrderService;

    public CustomerController(CustomerService customerService, CustomerOrderService customerOrderService) {
        this.customerService = customerService;
        this.customerOrderService = customerOrderService;
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        if (customer.getName() == null || customer.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (customer.getEmail() == null || customer.getEmail().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Email is required"));
        }
        if (customer.getCity() == null || customer.getCity().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "City is required"));
        }
        customer.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.saveCustomer(customer));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Customer not found with id " + id));
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<?> getOrdersByCustomer(@PathVariable Long id) {
        if (customerService.getCustomerById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Customer not found with id " + id));
        }
        return ResponseEntity.ok(customerOrderService.getOrdersByCustomerId(id));
    }

    @PostMapping("/{id}/orders")
    public ResponseEntity<?> createCustomerOrderForCustomer(@PathVariable Long id, @RequestBody CustomerOrder customerOrder) {
        if (customerOrder.getOrderNo() == null || customerOrder.getOrderNo().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Order no is required"));
        }
        if (customerOrder.getAmount() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Amount is required"));
        }
        Optional<CustomerOrder> saved = customerOrderService.createCustomerOrderForCustomer(id, customerOrder);
        if (saved.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(saved.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Customer not found with id " + id));
    }
}
