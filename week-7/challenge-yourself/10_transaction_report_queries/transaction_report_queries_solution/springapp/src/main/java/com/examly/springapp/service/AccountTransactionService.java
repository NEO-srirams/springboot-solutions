package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.examly.springapp.model.AccountTransaction;

public interface AccountTransactionService {

    List<AccountTransaction> getAccountTransactionByType(String type);

    List<AccountTransaction> searchAccountTransaction(String keyword);

    List<AccountTransaction> getAccountTransactionByAmountRange(Double min, Double max);

    List<AccountTransaction> filterAccountTransaction(String type, Double min);

    List<AccountTransaction> eitherAccountTransaction(String type, String keyword);

    List<AccountTransaction> getAccountTransactionAboveAmount(Double min);

    List<AccountTransaction> getAccountTransactionUpToAmount(Double max);

    int updateStatusByType(String type, String status);

    Page<AccountTransaction> getAccountTransactionPage(int page, int size, String sortBy, String direction);

    long countAccountTransactionByType(String type);

    Page<AccountTransaction> getAccountTransactionByTypePage(String type, int page, int size);
}
