package com.examly.springapp.dto;

import java.math.BigDecimal;

public class MenuItemRequestDTO {

    private String name;
    private String category;
    private BigDecimal price;
    private Boolean available;
    private Integer preparationMinutes;

    public MenuItemRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getPreparationMinutes() {
        return preparationMinutes;
    }

    public void setPreparationMinutes(Integer preparationMinutes) {
        this.preparationMinutes = preparationMinutes;
    }
}
