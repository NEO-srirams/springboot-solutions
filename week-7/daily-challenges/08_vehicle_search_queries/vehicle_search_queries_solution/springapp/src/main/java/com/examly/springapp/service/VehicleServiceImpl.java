package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Vehicle;
import com.examly.springapp.repository.VehicleRepository;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public List<Vehicle> getVehicleByBrand(String brand) {
        return vehicleRepository.findByBrandIgnoreCaseOrderByPriceDesc(brand);
    }

    @Override
    public List<Vehicle> searchVehicle(String keyword) {
        return vehicleRepository.findByModelContainingIgnoreCase(keyword);
    }

    @Override
    public List<Vehicle> getVehicleByPriceRange(Double min, Double max) {
        return vehicleRepository.findByPriceBetween(min, max);
    }

    @Override
    public List<Vehicle> filterVehicle(String brand, Double min) {
        return vehicleRepository.findByBrandIgnoreCaseAndPriceGreaterThanEqual(brand, min);
    }

    @Override
    public List<Vehicle> eitherVehicle(String brand, String keyword) {
        return vehicleRepository.findByBrandIgnoreCaseOrModelContainingIgnoreCase(brand, keyword);
    }

    @Override
    public List<Vehicle> getVehicleAbovePrice(Double min) {
        return vehicleRepository.findAbovePrice(min);
    }

    @Override
    public List<Vehicle> getVehicleUpToPrice(Double max) {
        return vehicleRepository.findUpToPriceNative(max);
    }

    @Override
    public int updateStatusByBrand(String brand, String status) {
        return vehicleRepository.updateStatusByBrand(brand, status);
    }

    @Override
    public Page<Vehicle> getVehiclePage(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return vehicleRepository.findAll(PageRequest.of(page, size, sort));
    }
}
