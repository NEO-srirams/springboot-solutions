package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Drive;
import com.examly.springapp.repository.DriveRepository;

@Service
public class DriveServiceImpl implements DriveService {

    private final DriveRepository driveRepository;

    public DriveServiceImpl(DriveRepository driveRepository) {
        this.driveRepository = driveRepository;
    }

    @Override
    public Drive saveDrive(Drive drive) {
        return driveRepository.save(drive);
    }

    @Override
    public List<Drive> getAllDrives() {
        return driveRepository.findAll();
    }

    @Override
    public Optional<Drive> getDriveById(Long id) {
        return driveRepository.findById(id);
    }
}
