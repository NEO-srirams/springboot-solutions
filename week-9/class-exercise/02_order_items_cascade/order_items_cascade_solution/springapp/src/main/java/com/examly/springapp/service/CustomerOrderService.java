package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.OrderItem;
import com.examly.springapp.model.CustomerOrder;

public interface CustomerOrderService {

    CustomerOrder saveCustomerOrder(CustomerOrder customerOrder);

    List<CustomerOrder> getAllOrders();

    Optional<CustomerOrder> getCustomerOrderById(Long id);

    Optional<OrderItem> addOrderItem(Long customerOrderId, OrderItem orderItem);

    boolean removeOrderItem(Long customerOrderId, Long orderItemId);

    void deleteCustomerOrder(Long id);
}
