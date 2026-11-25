package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.VehicleRequestDTO;
import com.examly.springapp.model.Vehicle;
import com.examly.springapp.repository.VehicleRepository;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public Optional<Vehicle> updateVehicle(Long id, VehicleRequestDTO request) {
        Optional<Vehicle> existing = vehicleRepository.findById(id);
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        Vehicle vehicle = existing.get();
        vehicle.setOwnerName(request.getOwnerName());
        vehicle.setModel(request.getModel());
        vehicle.setFuelType(request.getFuelType());
        return Optional.of(vehicleRepository.save(vehicle));
    }

    @Override
    public boolean deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            return false;
        }
        vehicleRepository.deleteById(id);
        return true;
    }
}
