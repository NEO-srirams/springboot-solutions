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
import com.examly.springapp.model.Employee;
import com.examly.springapp.model.Address;
import com.examly.springapp.service.EmployeeService;
import com.examly.springapp.service.AddressService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AddressService addressService;

    public EmployeeController(EmployeeService employeeService, AddressService addressService) {
        this.employeeService = employeeService;
        this.addressService = addressService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            return ResponseEntity.ok(employee.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Employee not found with id " + id));
    }

    @GetMapping("/{id}/address")
    public ResponseEntity<?> getAddressByEmployee(@PathVariable Long id) {
        if (employeeService.getEmployeeById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Employee not found with id " + id));
        }
        Optional<Address> address = addressService.getAddressByEmployeeId(id);
        if (address.isPresent()) {
            return ResponseEntity.ok(address.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "No Address found for Employee with id " + id));
    }

    @PostMapping("/{id}/address")
    public ResponseEntity<?> createAddressForEmployee(@PathVariable Long id, @RequestBody Address address) {
        if (address.getStreet() == null || address.getStreet().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Street is required"));
        }
        if (address.getCity() == null || address.getCity().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "City is required"));
        }
        if (address.getPincode() == null || address.getPincode().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Pincode is required"));
        }
        if (employeeService.getEmployeeById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Employee not found with id " + id));
        }
        if (addressService.employeeHasAddress(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Employee already has an address"));
        }
        Optional<Address> saved = addressService.createAddressForEmployee(id, address);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved.get());
    }
}
