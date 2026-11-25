package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.TransactionLog;

public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {
}
