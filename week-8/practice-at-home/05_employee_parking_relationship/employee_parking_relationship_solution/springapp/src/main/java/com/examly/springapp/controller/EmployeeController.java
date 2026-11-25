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

import com.examly.springapp.dto.EmployeeResponseDTO;
import com.examly.springapp.dto.ParkingSlotResponseDTO;
import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.Employee;
import com.examly.springapp.model.ParkingSlot;
import com.examly.springapp.service.EmployeeService;
import com.examly.springapp.service.ParkingSlotService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ParkingSlotService parkingSlotService;

    public EmployeeController(EmployeeService employeeService, ParkingSlotService parkingSlotService) {
        this.employeeService = employeeService;
        this.parkingSlotService = parkingSlotService;
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
        if (employee.getName() == null || employee.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (employee.getDepartment() == null || employee.getDepartment().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Department is required"));
        }
        employee.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toEmployeeResponse(employeeService.saveEmployee(employee)));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> result = new ArrayList<>();
        for (Employee x : employeeService.getAllEmployees()) {
            result.add(toEmployeeResponse(x));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        if (employee.isPresent()) {
            return ResponseEntity.ok(toEmployeeResponse(employee.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Employee not found with id " + id));
    }

    @GetMapping("/{id}/parking-slot")
    public ResponseEntity<?> getParkingSlotByEmployee(@PathVariable Long id) {
        if (employeeService.getEmployeeById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Employee not found with id " + id));
        }
        Optional<ParkingSlot> parkingSlot = parkingSlotService.getParkingSlotByEmployeeId(id);
        if (parkingSlot.isPresent()) {
            return ResponseEntity.ok(toParkingSlotResponse(parkingSlot.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "No ParkingSlot found for Employee with id " + id));
    }

    @PostMapping("/{id}/parking-slot")
    public ResponseEntity<?> createParkingSlotForEmployee(@PathVariable Long id, @RequestBody ParkingSlot parkingSlot) {
        if (parkingSlot.getSlotNo() == null || parkingSlot.getSlotNo().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Slot no is required"));
        }
        if (parkingSlot.getBlock() == null || parkingSlot.getBlock().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Block is required"));
        }
        if (employeeService.getEmployeeById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Employee not found with id " + id));
        }
        if (parkingSlotService.employeeHasParkingSlot(id)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Employee already has a parking slot"));
        }
        Optional<ParkingSlot> saved = parkingSlotService.createParkingSlotForEmployee(id, parkingSlot);
        return ResponseEntity.status(HttpStatus.CREATED).body(toParkingSlotResponse(saved.get()));
    }

    private EmployeeResponseDTO toEmployeeResponse(Employee x) {
        return new EmployeeResponseDTO(x.getId(), x.getName(), x.getDepartment(), x.getParkingSlot() == null ? null : x.getParkingSlot().getId());
    }

    private ParkingSlotResponseDTO toParkingSlotResponse(ParkingSlot x) {
        return new ParkingSlotResponseDTO(x.getId(), x.getSlotNo(), x.getBlock(), x.getVehicleNo(), x.getEmployee().getId(), x.getEmployee().getName());
    }
}
