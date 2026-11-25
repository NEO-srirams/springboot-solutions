package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Product;
import com.examly.springapp.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return productRepository.findByCategoryIgnoreCaseOrderByPriceDesc(category);
    }

    @Override
    public List<Product> searchProduct(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public List<Product> getProductByPriceRange(Double min, Double max) {
        return productRepository.findByPriceBetween(min, max);
    }

    @Override
    public List<Product> filterProduct(String category, Double min) {
        return productRepository.findByCategoryIgnoreCaseAndPriceGreaterThanEqual(category, min);
    }

    @Override
    public List<Product> eitherProduct(String category, String keyword) {
        return productRepository.findByCategoryIgnoreCaseOrNameContainingIgnoreCase(category, keyword);
    }

    @Override
    public List<Product> getProductAbovePrice(Double min) {
        return productRepository.findAbovePrice(min);
    }

    @Override
    public List<Product> getProductUpToPrice(Double max) {
        return productRepository.findUpToPriceNative(max);
    }

    @Override
    public int updateStatusByCategory(String category, String status) {
        return productRepository.updateStatusByCategory(category, status);
    }

    @Override
    public Page<Product> getProductPage(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return productRepository.findAll(PageRequest.of(page, size, sort));
    }
}
