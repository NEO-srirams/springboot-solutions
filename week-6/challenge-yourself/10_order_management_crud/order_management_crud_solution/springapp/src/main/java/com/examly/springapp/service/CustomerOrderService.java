package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.dto.CustomerOrderRequestDTO;
import com.examly.springapp.model.CustomerOrder;

public interface CustomerOrderService {

    CustomerOrder saveCustomerOrder(CustomerOrder customerOrder);

    List<CustomerOrder> getAllOrders();

    Optional<CustomerOrder> getCustomerOrderById(Long id);

    Optional<CustomerOrder> updateCustomerOrder(Long id, CustomerOrderRequestDTO request);

    boolean deleteCustomerOrder(Long id);
}
