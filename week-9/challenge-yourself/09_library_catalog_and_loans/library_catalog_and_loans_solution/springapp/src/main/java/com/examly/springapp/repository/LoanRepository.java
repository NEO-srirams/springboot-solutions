package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
