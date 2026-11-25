package com.examly.springapp.dto;

import java.time.LocalDate;
import java.util.List;

public class CustomerOrderResponseDTO {

    private Long id;
    private String customerName;
    private LocalDate orderDate;
    private String status;
    private List<OrderItemResponseDTO> items;

    public CustomerOrderResponseDTO() {
    }

    public CustomerOrderResponseDTO(Long id, String customerName, LocalDate orderDate, String status, List<OrderItemResponseDTO> items) {
        this.id = id;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.status = status;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<OrderItemResponseDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemResponseDTO> items) {
        this.items = items;
    }
}
