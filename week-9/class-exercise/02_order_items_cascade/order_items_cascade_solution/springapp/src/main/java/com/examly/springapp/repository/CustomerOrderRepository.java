package com.examly.springapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.CustomerOrder;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

    @Query("SELECT DISTINCT x FROM CustomerOrder x LEFT JOIN FETCH x.items ORDER BY x.id")
    List<CustomerOrder> findAllWithItems();
}
