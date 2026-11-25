package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.TransactionLog;
import com.examly.springapp.repository.TransactionLogRepository;

@Service
public class TransactionLogServiceImpl implements TransactionLogService {

    private final TransactionLogRepository transactionLogRepository;

    public TransactionLogServiceImpl(TransactionLogRepository transactionLogRepository) {
        this.transactionLogRepository = transactionLogRepository;
    }

    @Override
    public TransactionLog saveTransactionLog(TransactionLog transactionLog) {
        return transactionLogRepository.save(transactionLog);
    }

    @Override
    public List<TransactionLog> getAllTransactions() {
        return transactionLogRepository.findAll();
    }

    @Override
    public Optional<TransactionLog> getTransactionLogById(Long id) {
        return transactionLogRepository.findById(id);
    }
}
