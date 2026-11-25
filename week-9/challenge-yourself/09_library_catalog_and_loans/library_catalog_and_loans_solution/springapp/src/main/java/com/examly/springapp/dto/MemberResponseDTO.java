package com.examly.springapp.dto;

import java.util.List;

public class MemberResponseDTO {

    private Long id;
    private String name;
    private String email;
    private List<LoanResponseDTO> loans;

    public MemberResponseDTO() {
    }

    public MemberResponseDTO(Long id, String name, String email, List<LoanResponseDTO> loans) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.loans = loans;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<LoanResponseDTO> getLoans() {
        return loans;
    }

    public void setLoans(List<LoanResponseDTO> loans) {
        this.loans = loans;
    }
}
