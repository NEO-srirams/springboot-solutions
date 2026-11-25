package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.TransactionLog;

public interface TransactionLogService {

    TransactionLog saveTransactionLog(TransactionLog transactionLog);

    List<TransactionLog> getAllTransactions();

    Optional<TransactionLog> getTransactionLogById(Long id);
}
