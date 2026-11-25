package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.CustomerOrder;
import com.examly.springapp.repository.CustomerOrderRepository;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;

    public CustomerOrderServiceImpl(CustomerOrderRepository customerOrderRepository) {
        this.customerOrderRepository = customerOrderRepository;
    }

    @Override
    public List<CustomerOrder> getCustomerOrderByCity(String city) {
        return customerOrderRepository.findByCityIgnoreCaseOrderByAmountDesc(city);
    }

    @Override
    public List<CustomerOrder> searchCustomerOrder(String keyword) {
        return customerOrderRepository.findByCustomerNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<CustomerOrder> getCustomerOrderByAmountRange(Double min, Double max) {
        return customerOrderRepository.findByAmountBetween(min, max);
    }

    @Override
    public List<CustomerOrder> filterCustomerOrder(String city, Double min) {
        return customerOrderRepository.findByCityIgnoreCaseAndAmountGreaterThanEqual(city, min);
    }

    @Override
    public List<CustomerOrder> eitherCustomerOrder(String city, String keyword) {
        return customerOrderRepository.findByCityIgnoreCaseOrCustomerNameContainingIgnoreCase(city, keyword);
    }

    @Override
    public List<CustomerOrder> getCustomerOrderAboveAmount(Double min) {
        return customerOrderRepository.findAboveAmount(min);
    }

    @Override
    public List<CustomerOrder> getCustomerOrderUpToAmount(Double max) {
        return customerOrderRepository.findUpToAmountNative(max);
    }

    @Override
    public int updateStatusByCity(String city, String status) {
        return customerOrderRepository.updateStatusByCity(city, status);
    }

    @Override
    public Page<CustomerOrder> getCustomerOrderPage(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return customerOrderRepository.findAll(PageRequest.of(page, size, sort));
    }

    @Override
    public long countCustomerOrderByCity(String city) {
        return customerOrderRepository.countByCityIgnoreCase(city);
    }

    @Override
    public Page<CustomerOrder> getCustomerOrderByCityPage(String city, int page, int size) {
        return customerOrderRepository.findByCityIgnoreCase(city, PageRequest.of(page, size));
    }
}
