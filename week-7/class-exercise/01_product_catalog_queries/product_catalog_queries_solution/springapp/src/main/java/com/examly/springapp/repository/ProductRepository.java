package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryIgnoreCaseOrderByPriceDesc(String category);

    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByPriceBetween(Double min, Double max);

    List<Product> findByCategoryIgnoreCaseAndPriceGreaterThanEqual(String category, Double min);

    List<Product> findByCategoryIgnoreCaseOrNameContainingIgnoreCase(String category, String keyword);

    @Query("SELECT x FROM Product x WHERE x.price > :min ORDER BY x.price DESC")
    List<Product> findAbovePrice(@Param("min") Double min);

    @Query(value = "SELECT * FROM products WHERE price <= :max ORDER BY price ASC", nativeQuery = true)
    List<Product> findUpToPriceNative(@Param("max") Double max);

    @Modifying
    @Transactional
    @Query("UPDATE Product x SET x.status = :status WHERE LOWER(x.category) = LOWER(:category)")
    int updateStatusByCategory(@Param("category") String category, @Param("status") String status);
}
