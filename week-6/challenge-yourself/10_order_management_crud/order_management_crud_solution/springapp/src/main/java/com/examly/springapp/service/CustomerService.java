package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.dto.CustomerRequestDTO;
import com.examly.springapp.model.Customer;

public interface CustomerService {

    Customer saveCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Optional<Customer> getCustomerById(Long id);

    Optional<Customer> updateCustomer(Long id, CustomerRequestDTO request);

    boolean deleteCustomer(Long id);
}
