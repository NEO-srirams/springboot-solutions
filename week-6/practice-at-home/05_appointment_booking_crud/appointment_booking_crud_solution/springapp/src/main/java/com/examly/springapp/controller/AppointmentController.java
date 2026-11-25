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

import com.examly.springapp.dto.AppointmentRequestDTO;
import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.Appointment;
import com.examly.springapp.service.AppointmentService;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
        if (appointment.getPatientName() == null || appointment.getPatientName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Patient name is required"));
        }
        if (appointment.getDoctor() == null || appointment.getDoctor().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Doctor is required"));
        }
        if (appointment.getAppointmentDate() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Appointment date is required"));
        }
        if (appointment.getFee() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Fee is required"));
        }
        appointment.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.saveAppointment(appointment));
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAppointmentById(@PathVariable Long id) {
        Optional<Appointment> appointment = appointmentService.getAppointmentById(id);
        if (appointment.isPresent()) {
            return ResponseEntity.ok(appointment.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Appointment not found with id " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable Long id, @RequestBody AppointmentRequestDTO request) {
        if (request.getPatientName() == null || request.getPatientName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Patient name is required"));
        }
        if (request.getDoctor() == null || request.getDoctor().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Doctor is required"));
        }
        if (request.getAppointmentDate() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Appointment date is required"));
        }
        if (request.getFee() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Fee is required"));
        }
        Optional<Appointment> updated = appointmentService.updateAppointment(id, request);
        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Appointment not found with id " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        if (appointmentService.deleteAppointment(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Appointment not found with id " + id));
    }
}
