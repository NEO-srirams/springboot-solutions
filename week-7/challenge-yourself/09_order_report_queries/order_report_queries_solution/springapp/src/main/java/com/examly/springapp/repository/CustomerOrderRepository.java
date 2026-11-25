package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

    List<CustomerOrder> findByCityIgnoreCaseOrderByAmountDesc(String city);

    List<CustomerOrder> findByCustomerNameContainingIgnoreCase(String keyword);

    List<CustomerOrder> findByAmountBetween(Double min, Double max);

    List<CustomerOrder> findByCityIgnoreCaseAndAmountGreaterThanEqual(String city, Double min);

    List<CustomerOrder> findByCityIgnoreCaseOrCustomerNameContainingIgnoreCase(String city, String keyword);

    @Query("SELECT x FROM CustomerOrder x WHERE x.amount > :min ORDER BY x.amount DESC")
    List<CustomerOrder> findAboveAmount(@Param("min") Double min);

    @Query(value = "SELECT * FROM customer_orders WHERE amount <= :max ORDER BY amount ASC", nativeQuery = true)
    List<CustomerOrder> findUpToAmountNative(@Param("max") Double max);

    @Modifying
    @Transactional
    @Query("UPDATE CustomerOrder x SET x.status = :status WHERE LOWER(x.city) = LOWER(:city)")
    int updateStatusByCity(@Param("city") String city, @Param("status") String status);

    long countByCityIgnoreCase(String city);

    Page<CustomerOrder> findByCityIgnoreCase(String city, Pageable pageable);
}
