package com.examly.springapp.dto;

import java.time.LocalDate;
import java.util.List;

public class CustomerOrderRequestDTO {

    private String customerName;
    private LocalDate orderDate;
    private String status;
    private List<OrderItemRequestDTO> items;

    public CustomerOrderRequestDTO() {
    }

    public CustomerOrderRequestDTO(String customerName, LocalDate orderDate, String status, List<OrderItemRequestDTO> items) {
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.status = status;
        this.items = items;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequestDTO> items) {
        this.items = items;
    }
}
