package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByBrandIgnoreCaseOrderByPriceDesc(String brand);

    List<Vehicle> findByModelContainingIgnoreCase(String keyword);

    List<Vehicle> findByPriceBetween(Double min, Double max);

    List<Vehicle> findByBrandIgnoreCaseAndPriceGreaterThanEqual(String brand, Double min);

    List<Vehicle> findByBrandIgnoreCaseOrModelContainingIgnoreCase(String brand, String keyword);

    @Query("SELECT x FROM Vehicle x WHERE x.price > :min ORDER BY x.price DESC")
    List<Vehicle> findAbovePrice(@Param("min") Double min);

    @Query(value = "SELECT * FROM vehicles WHERE price <= :max ORDER BY price ASC", nativeQuery = true)
    List<Vehicle> findUpToPriceNative(@Param("max") Double max);

    @Modifying
    @Transactional
    @Query("UPDATE Vehicle x SET x.status = :status WHERE LOWER(x.brand) = LOWER(:brand)")
    int updateStatusByBrand(@Param("brand") String brand, @Param("status") String status);
}
