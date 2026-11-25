package com.examly.springapp.dto;

import java.util.List;

public class MemberRequestDTO {

    private String name;
    private String email;
    private List<LoanRequestDTO> loans;

    public MemberRequestDTO() {
    }

    public MemberRequestDTO(String name, String email, List<LoanRequestDTO> loans) {
        this.name = name;
        this.email = email;
        this.loans = loans;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<LoanRequestDTO> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanRequestDTO> loans) {
        this.loans = loans;
    }
}
