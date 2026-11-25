package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Drive;

public interface DriveService {

    Drive saveDrive(Drive drive);

    List<Drive> getAllDrives();

    Optional<Drive> getDriveById(Long id);
}
