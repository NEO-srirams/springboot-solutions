package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.AccountTransaction;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {

    List<AccountTransaction> findByTypeIgnoreCaseOrderByAmountDesc(String type);

    List<AccountTransaction> findByAccountNoContainingIgnoreCase(String keyword);

    List<AccountTransaction> findByAmountBetween(Double min, Double max);

    List<AccountTransaction> findByTypeIgnoreCaseAndAmountGreaterThanEqual(String type, Double min);

    List<AccountTransaction> findByTypeIgnoreCaseOrAccountNoContainingIgnoreCase(String type, String keyword);

    @Query("SELECT x FROM AccountTransaction x WHERE x.amount > :min ORDER BY x.amount DESC")
    List<AccountTransaction> findAboveAmount(@Param("min") Double min);

    @Query(value = "SELECT * FROM account_transactions WHERE amount <= :max ORDER BY amount ASC", nativeQuery = true)
    List<AccountTransaction> findUpToAmountNative(@Param("max") Double max);

    @Modifying
    @Transactional
    @Query("UPDATE AccountTransaction x SET x.status = :status WHERE LOWER(x.type) = LOWER(:type)")
    int updateStatusByType(@Param("type") String type, @Param("status") String status);

    long countByTypeIgnoreCase(String type);

    Page<AccountTransaction> findByTypeIgnoreCase(String type, Pageable pageable);
}
