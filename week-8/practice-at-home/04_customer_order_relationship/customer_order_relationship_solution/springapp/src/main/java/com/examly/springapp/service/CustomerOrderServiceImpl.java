package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.CustomerOrder;
import com.examly.springapp.model.Customer;
import com.examly.springapp.repository.CustomerRepository;
import com.examly.springapp.repository.CustomerOrderRepository;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final CustomerRepository customerRepository;

    public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository, CustomerRepository customerRepository) {
        this.customerOrderRepository = customerOrderRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerOrder saveCustomerOrder(CustomerOrder customerOrder) {
        return customerOrderRepository.save(customerOrder);
    }

    @Override
    public List<CustomerOrder> getAllOrders() {
        return customerOrderRepository.findAll();
    }

    @Override
    public Optional<CustomerOrder> getCustomerOrderById(Long id) {
        return customerOrderRepository.findById(id);
    }

    @Override
    public List<CustomerOrder> getOrdersByCustomerId(Long customerId) {
        return customerOrderRepository.findByCustomerId(customerId);
    }

    @Override
    public Optional<CustomerOrder> createCustomerOrderForCustomer(Long customerId, CustomerOrder customerOrder) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            return Optional.empty();
        }
        customerOrder.setId(null);
        customer.get().addCustomerOrder(customerOrder);
        return Optional.of(customerOrderRepository.save(customerOrder));
    }
}
