package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Account;

public interface AccountService {

    Account saveAccount(Account account);

    List<Account> getAllAccounts();

    Optional<Account> getAccountById(Long id);
}
