package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.dto.ProductRequestDTO;
import com.examly.springapp.model.Product;

public interface ProductService {

    Product saveProduct(Product product);

    List<Product> getAllProducts();

    Optional<Product> getProductById(Long id);

    Optional<Product> updateProduct(Long id, ProductRequestDTO request);

    boolean deleteProduct(Long id);
}
