package com.examly.springapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByEmployeeId(Long employeeId);

    boolean existsByEmployeeId(Long employeeId);
}
