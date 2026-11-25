package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.ParkingSlot;
import com.examly.springapp.model.Employee;
import com.examly.springapp.repository.EmployeeRepository;
import com.examly.springapp.repository.ParkingSlotRepository;

@Service
public class ParkingSlotServiceImpl implements ParkingSlotService {

    private final ParkingSlotRepository parkingSlotRepository;
    private final EmployeeRepository employeeRepository;

    public ParkingSlotServiceImpl(ParkingSlotRepository parkingSlotRepository, EmployeeRepository employeeRepository) {
        this.parkingSlotRepository = parkingSlotRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ParkingSlot saveParkingSlot(ParkingSlot parkingSlot) {
        return parkingSlotRepository.save(parkingSlot);
    }

    @Override
    public List<ParkingSlot> getAllParkingSlots() {
        return parkingSlotRepository.findAll();
    }

    @Override
    public Optional<ParkingSlot> getParkingSlotById(Long id) {
        return parkingSlotRepository.findById(id);
    }

    @Override
    public Optional<ParkingSlot> getParkingSlotByEmployeeId(Long employeeId) {
        return parkingSlotRepository.findByEmployeeId(employeeId);
    }

    @Override
    public boolean employeeHasParkingSlot(Long employeeId) {
        return parkingSlotRepository.existsByEmployeeId(employeeId);
    }

    @Override
    public Optional<ParkingSlot> createParkingSlotForEmployee(Long employeeId, ParkingSlot parkingSlot) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isEmpty()) {
            return Optional.empty();
        }
        parkingSlot.setId(null);
        employee.get().setParkingSlot(parkingSlot);
        return Optional.of(parkingSlotRepository.save(parkingSlot));
    }
}
