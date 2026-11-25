package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.dto.VehicleRequestDTO;
import com.examly.springapp.model.Vehicle;

public interface VehicleService {

    Vehicle saveVehicle(Vehicle vehicle);

    List<Vehicle> getAllVehicles();

    Optional<Vehicle> getVehicleById(Long id);

    Optional<Vehicle> updateVehicle(Long id, VehicleRequestDTO request);

    boolean deleteVehicle(Long id);
}
