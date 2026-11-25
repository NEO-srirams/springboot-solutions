package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.examly.springapp.model.CustomerOrder;

public interface CustomerOrderService {

    List<CustomerOrder> getCustomerOrderByCity(String city);

    List<CustomerOrder> searchCustomerOrder(String keyword);

    List<CustomerOrder> getCustomerOrderByAmountRange(Double min, Double max);

    List<CustomerOrder> filterCustomerOrder(String city, Double min);

    List<CustomerOrder> eitherCustomerOrder(String city, String keyword);

    List<CustomerOrder> getCustomerOrderAboveAmount(Double min);

    List<CustomerOrder> getCustomerOrderUpToAmount(Double max);

    int updateStatusByCity(String city, String status);

    Page<CustomerOrder> getCustomerOrderPage(int page, int size, String sortBy, String direction);

    long countCustomerOrderByCity(String city);

    Page<CustomerOrder> getCustomerOrderByCityPage(String city, int page, int size);
}
