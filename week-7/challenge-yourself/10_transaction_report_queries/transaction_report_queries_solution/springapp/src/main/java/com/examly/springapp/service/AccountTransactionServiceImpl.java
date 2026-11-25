package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.AccountTransaction;
import com.examly.springapp.repository.AccountTransactionRepository;

@Service
public class AccountTransactionServiceImpl implements AccountTransactionService {

    private final AccountTransactionRepository accountTransactionRepository;

    public AccountTransactionServiceImpl(AccountTransactionRepository accountTransactionRepository) {
        this.accountTransactionRepository = accountTransactionRepository;
    }

    @Override
    public List<AccountTransaction> getAccountTransactionByType(String type) {
        return accountTransactionRepository.findByTypeIgnoreCaseOrderByAmountDesc(type);
    }

    @Override
    public List<AccountTransaction> searchAccountTransaction(String keyword) {
        return accountTransactionRepository.findByAccountNoContainingIgnoreCase(keyword);
    }

    @Override
    public List<AccountTransaction> getAccountTransactionByAmountRange(Double min, Double max) {
        return accountTransactionRepository.findByAmountBetween(min, max);
    }

    @Override
    public List<AccountTransaction> filterAccountTransaction(String type, Double min) {
        return accountTransactionRepository.findByTypeIgnoreCaseAndAmountGreaterThanEqual(type, min);
    }

    @Override
    public List<AccountTransaction> eitherAccountTransaction(String type, String keyword) {
        return accountTransactionRepository.findByTypeIgnoreCaseOrAccountNoContainingIgnoreCase(type, keyword);
    }

    @Override
    public List<AccountTransaction> getAccountTransactionAboveAmount(Double min) {
        return accountTransactionRepository.findAboveAmount(min);
    }

    @Override
    public List<AccountTransaction> getAccountTransactionUpToAmount(Double max) {
        return accountTransactionRepository.findUpToAmountNative(max);
    }

    @Override
    public int updateStatusByType(String type, String status) {
        return accountTransactionRepository.updateStatusByType(type, status);
    }

    @Override
    public Page<AccountTransaction> getAccountTransactionPage(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return accountTransactionRepository.findAll(PageRequest.of(page, size, sort));
    }

    @Override
    public long countAccountTransactionByType(String type) {
        return accountTransactionRepository.countByTypeIgnoreCase(type);
    }

    @Override
    public Page<AccountTransaction> getAccountTransactionByTypePage(String type, int page, int size) {
        return accountTransactionRepository.findByTypeIgnoreCase(type, PageRequest.of(page, size));
    }
}
