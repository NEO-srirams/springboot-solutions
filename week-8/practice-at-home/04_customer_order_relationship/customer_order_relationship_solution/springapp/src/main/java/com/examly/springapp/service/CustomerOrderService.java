package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.CustomerOrder;
import com.examly.springapp.model.Customer;

public interface CustomerOrderService {

    CustomerOrder saveCustomerOrder(CustomerOrder customerOrder);

    List<CustomerOrder> getAllOrders();

    Optional<CustomerOrder> getCustomerOrderById(Long id);

    List<CustomerOrder> getOrdersByCustomerId(Long customerId);

    Optional<CustomerOrder> createCustomerOrderForCustomer(Long customerId, CustomerOrder customerOrder);
}
