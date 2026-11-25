package com.examly.springapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_no", nullable = false, unique = true, length = 12, updatable = false)
    private String accountNo;

    @Column(name = "holder_name", nullable = false, length = 100)
    private String holderName;

    @Column(name = "account_type", length = 10)
    private String accountType = "SAVINGS";

    @Column(name = "balance", precision = 15, scale = 2)
    private BigDecimal balance = new BigDecimal("0.00");

    @Column(name = "status", length = 10)
    private String status = "ACTIVE";

    @Column(name = "opened_on", updatable = false)
    private LocalDateTime openedOn = LocalDateTime.now();

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOpenedOn() {
        return openedOn;
    }

    public void setOpenedOn(LocalDateTime openedOn) {
        this.openedOn = openedOn;
    }
}
