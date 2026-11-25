package com.examly.springapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.AccountTransaction;
import com.examly.springapp.service.AccountTransactionService;

@RestController
@RequestMapping("/api/transactions")
public class AccountTransactionController {

    private final AccountTransactionService accountTransactionService;

    public AccountTransactionController(AccountTransactionService accountTransactionService) {
        this.accountTransactionService = accountTransactionService;
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<AccountTransaction>> getAccountTransactionByType(@PathVariable String type) {
        return ResponseEntity.ok(accountTransactionService.getAccountTransactionByType(type));
    }

    @GetMapping("/search")
    public ResponseEntity<List<AccountTransaction>> searchAccountTransaction(@RequestParam String keyword) {
        return ResponseEntity.ok(accountTransactionService.searchAccountTransaction(keyword));
    }

    @GetMapping("/amount-range")
    public ResponseEntity<?> getAccountTransactionByAmountRange(@RequestParam Double min, @RequestParam Double max) {
        if (min > max) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Minimum must not be greater than maximum"));
        }
        return ResponseEntity.ok(accountTransactionService.getAccountTransactionByAmountRange(min, max));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<AccountTransaction>> filterAccountTransaction(@RequestParam String type, @RequestParam Double minAmount) {
        return ResponseEntity.ok(accountTransactionService.filterAccountTransaction(type, minAmount));
    }

    @GetMapping("/either")
    public ResponseEntity<List<AccountTransaction>> eitherAccountTransaction(@RequestParam String type, @RequestParam String keyword) {
        return ResponseEntity.ok(accountTransactionService.eitherAccountTransaction(type, keyword));
    }

    @GetMapping("/above")
    public ResponseEntity<List<AccountTransaction>> getAccountTransactionAboveAmount(@RequestParam Double minAmount) {
        return ResponseEntity.ok(accountTransactionService.getAccountTransactionAboveAmount(minAmount));
    }

    @GetMapping("/native")
    public ResponseEntity<List<AccountTransaction>> getAccountTransactionUpToAmount(@RequestParam Double maxAmount) {
        return ResponseEntity.ok(accountTransactionService.getAccountTransactionUpToAmount(maxAmount));
    }

    @PatchMapping("/status")
    public ResponseEntity<?> updateStatusByType(@RequestParam String type, @RequestParam String status) {
        if (status == null || status.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Status is required"));
        }
        return ResponseEntity.ok(Map.of("updated", accountTransactionService.updateStatusByType(type, status)));
    }

    @GetMapping("/page")
    public ResponseEntity<?> getAccountTransactionPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String direction) {
        if (page < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Page must not be negative"));
        }
        if (size < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Size must be at least 1"));
        }
        Page<AccountTransaction> result = accountTransactionService.getAccountTransactionPage(page, size, sortBy, direction);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> countAccountTransactionByType(@RequestParam String type) {
        return ResponseEntity.ok(Map.of("count", accountTransactionService.countAccountTransactionByType(type)));
    }

    @GetMapping("/type/{type}/page")
    public ResponseEntity<?> getAccountTransactionByTypePage(@PathVariable String type,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size) {
        if (page < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Page must not be negative"));
        }
        if (size < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Size must be at least 1"));
        }
        return ResponseEntity.ok(accountTransactionService.getAccountTransactionByTypePage(type, page, size));
    }
}
