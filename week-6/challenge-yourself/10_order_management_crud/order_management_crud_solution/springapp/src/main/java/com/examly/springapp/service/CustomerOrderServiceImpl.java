package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.CustomerOrderRequestDTO;
import com.examly.springapp.model.CustomerOrder;
import com.examly.springapp.repository.CustomerOrderRepository;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;

    public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository) {
        this.customerOrderRepository = customerOrderRepository;
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
    public Optional<CustomerOrder> updateCustomerOrder(Long id, CustomerOrderRequestDTO request) {
        Optional<CustomerOrder> existing = customerOrderRepository.findById(id);
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        CustomerOrder customerOrder = existing.get();
        customerOrder.setCustomerName(request.getCustomerName());
        customerOrder.setAmount(request.getAmount());
        customerOrder.setStatus(request.getStatus());
        return Optional.of(customerOrderRepository.save(customerOrder));
    }

    @Override
    public boolean deleteCustomerOrder(Long id) {
        if (!customerOrderRepository.existsById(id)) {
            return false;
        }
        customerOrderRepository.deleteById(id);
        return true;
    }
}
