package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.ParkingSlot;
import com.examly.springapp.model.Employee;

public interface ParkingSlotService {

    ParkingSlot saveParkingSlot(ParkingSlot parkingSlot);

    List<ParkingSlot> getAllParkingSlots();

    Optional<ParkingSlot> getParkingSlotById(Long id);

    Optional<ParkingSlot> getParkingSlotByEmployeeId(Long employeeId);

    boolean employeeHasParkingSlot(Long employeeId);

    Optional<ParkingSlot> createParkingSlotForEmployee(Long employeeId, ParkingSlot parkingSlot);
}
