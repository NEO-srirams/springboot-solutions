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
import com.examly.springapp.model.Drive;
import com.examly.springapp.service.DriveService;

@RestController
@RequestMapping("/api/drives")
public class DriveController {

    private final DriveService driveService;

    public DriveController(DriveService driveService) {
        this.driveService = driveService;
    }

    @PostMapping
    public ResponseEntity<?> createDrive(@RequestBody Drive drive) {
        if (drive.getRole() == null || drive.getRole().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Role is required"));
        }
        if (drive.getCompanyName() == null || drive.getCompanyName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Company name is required"));
        }
        if (drive.getCtc() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Ctc is required"));
        }
        if (drive.getDriveDate() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Drive date is required"));
        }
        drive.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(driveService.saveDrive(drive));
    }

    @GetMapping
    public ResponseEntity<List<Drive>> getAllDrives() {
        return ResponseEntity.ok(driveService.getAllDrives());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDriveById(@PathVariable Long id) {
        Optional<Drive> drive = driveService.getDriveById(id);
        if (drive.isPresent()) {
            return ResponseEntity.ok(drive.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Drive not found with id " + id));
    }
}
