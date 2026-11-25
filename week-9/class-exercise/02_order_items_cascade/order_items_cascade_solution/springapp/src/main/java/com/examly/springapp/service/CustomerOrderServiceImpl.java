package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.OrderItem;
import com.examly.springapp.model.CustomerOrder;
import com.examly.springapp.repository.OrderItemRepository;
import com.examly.springapp.repository.CustomerOrderRepository;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final OrderItemRepository orderItemRepository;

    public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository, OrderItemRepository orderItemRepository) {
        this.customerOrderRepository = customerOrderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public CustomerOrder saveCustomerOrder(CustomerOrder customerOrder) {
        return customerOrderRepository.save(customerOrder);
    }

    @Override
    public List<CustomerOrder> getAllOrders() {
        return customerOrderRepository.findAllWithItems();
    }

    @Override
    public Optional<CustomerOrder> getCustomerOrderById(Long id) {
        return customerOrderRepository.findById(id);
    }

    @Transactional
    @Override
    public Optional<OrderItem> addOrderItem(Long customerOrderId, OrderItem orderItem) {
        Optional<CustomerOrder> customerOrder = customerOrderRepository.findById(customerOrderId);
        if (customerOrder.isEmpty()) {
            return Optional.empty();
        }
        orderItem.setId(null);
        customerOrder.get().addOrderItem(orderItem);
        return Optional.of(orderItemRepository.save(orderItem));
    }

    @Transactional
    @Override
    public boolean removeOrderItem(Long customerOrderId, Long orderItemId) {
        Optional<CustomerOrder> customerOrder = customerOrderRepository.findById(customerOrderId);
        if (customerOrder.isEmpty()) {
            return false;
        }
        for (OrderItem x : customerOrder.get().getItems()) {
            if (x.getId().equals(orderItemId)) {
                customerOrder.get().removeOrderItem(x);
                customerOrderRepository.save(customerOrder.get());
                return true;
            }
        }
        return false;
    }

    @Transactional
    @Override
    public void deleteCustomerOrder(Long id) {
        customerOrderRepository.deleteById(id);
    }
}
