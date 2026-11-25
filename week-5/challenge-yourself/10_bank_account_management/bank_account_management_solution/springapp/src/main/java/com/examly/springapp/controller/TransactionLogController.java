package com.examly.springapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.TransactionLog;
import com.examly.springapp.service.TransactionLogService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionLogController {

    private final TransactionLogService transactionLogService;

    public TransactionLogController(TransactionLogService transactionLogService) {
        this.transactionLogService = transactionLogService;
    }

    @PostMapping
    public ResponseEntity<?> createTransactionLog(@RequestBody TransactionLog transactionLog) {
        if (transactionLog.getTxnRef() == null || transactionLog.getTxnRef().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Txn ref is required"));
        }
        if (transactionLog.getAccountNo() == null || transactionLog.getAccountNo().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Account no is required"));
        }
        if (transactionLog.getTxnType() == null || transactionLog.getTxnType().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Txn type is required"));
        }
        if (transactionLog.getAmount() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Amount is required"));
        }
        transactionLog.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionLogService.saveTransactionLog(transactionLog));
    }

    @GetMapping
    public ResponseEntity<List<TransactionLog>> getAllTransactions() {
        return ResponseEntity.ok(transactionLogService.getAllTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionLogById(@PathVariable Long id) {
        Optional<TransactionLog> transactionLog = transactionLogService.getTransactionLogById(id);
        if (transactionLog.isPresent()) {
            return ResponseEntity.ok(transactionLog.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "TransactionLog not found with id " + id));
    }
}
