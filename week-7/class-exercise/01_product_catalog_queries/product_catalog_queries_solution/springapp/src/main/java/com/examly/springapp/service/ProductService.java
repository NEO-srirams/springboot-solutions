package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.examly.springapp.model.Product;

public interface ProductService {

    List<Product> getProductByCategory(String category);

    List<Product> searchProduct(String keyword);

    List<Product> getProductByPriceRange(Double min, Double max);

    List<Product> filterProduct(String category, Double min);

    List<Product> eitherProduct(String category, String keyword);

    List<Product> getProductAbovePrice(Double min);

    List<Product> getProductUpToPrice(Double max);

    int updateStatusByCategory(String category, String status);

    Page<Product> getProductPage(int page, int size, String sortBy, String direction);
}
