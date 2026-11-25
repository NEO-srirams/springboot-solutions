package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
