package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.CustomerRequestDTO;
import com.examly.springapp.model.Customer;
import com.examly.springapp.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Optional<Customer> updateCustomer(Long id, CustomerRequestDTO request) {
        Optional<Customer> existing = customerRepository.findById(id);
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        Customer customer = existing.get();
        customer.setName(request.getName());
        customer.setPhone(request.getPhone());
        customer.setCity(request.getCity());
        return Optional.of(customerRepository.save(customer));
    }

    @Override
    public boolean deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            return false;
        }
        customerRepository.deleteById(id);
        return true;
    }
}
