package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Address;
import com.examly.springapp.model.Employee;

public interface AddressService {

    Address saveAddress(Address address);

    List<Address> getAllAddresses();

    Optional<Address> getAddressById(Long id);

    Optional<Address> getAddressByEmployeeId(Long employeeId);

    boolean employeeHasAddress(Long employeeId);

    Optional<Address> createAddressForEmployee(Long employeeId, Address address);
}
