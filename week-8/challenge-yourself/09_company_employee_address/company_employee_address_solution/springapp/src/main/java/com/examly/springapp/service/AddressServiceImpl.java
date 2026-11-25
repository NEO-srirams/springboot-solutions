package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Address;
import com.examly.springapp.model.Employee;
import com.examly.springapp.repository.EmployeeRepository;
import com.examly.springapp.repository.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final EmployeeRepository employeeRepository;

    public AddressServiceImpl(AddressRepository addressRepository, EmployeeRepository employeeRepository) {
        this.addressRepository = addressRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Optional<Address> getAddressById(Long id) {
        return addressRepository.findById(id);
    }

    @Override
    public Optional<Address> getAddressByEmployeeId(Long employeeId) {
        return addressRepository.findByEmployeeId(employeeId);
    }

    @Override
    public boolean employeeHasAddress(Long employeeId) {
        return addressRepository.existsByEmployeeId(employeeId);
    }

    @Override
    public Optional<Address> createAddressForEmployee(Long employeeId, Address address) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isEmpty()) {
            return Optional.empty();
        }
        address.setId(null);
        employee.get().setAddress(address);
        return Optional.of(addressRepository.save(address));
    }
}
