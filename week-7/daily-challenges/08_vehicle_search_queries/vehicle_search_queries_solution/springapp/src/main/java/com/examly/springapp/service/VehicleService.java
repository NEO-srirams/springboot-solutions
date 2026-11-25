package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.examly.springapp.model.Vehicle;

public interface VehicleService {

    List<Vehicle> getVehicleByBrand(String brand);

    List<Vehicle> searchVehicle(String keyword);

    List<Vehicle> getVehicleByPriceRange(Double min, Double max);

    List<Vehicle> filterVehicle(String brand, Double min);

    List<Vehicle> eitherVehicle(String brand, String keyword);

    List<Vehicle> getVehicleAbovePrice(Double min);

    List<Vehicle> getVehicleUpToPrice(Double max);

    int updateStatusByBrand(String brand, String status);

    Page<Vehicle> getVehiclePage(int page, int size, String sortBy, String direction);
}
