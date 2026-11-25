package com.examly.springapp.dto;

import java.time.LocalDate;

public class LoanResponseDTO {

    private Long id;
    private String bookTitle;
    private LocalDate borrowedOn;
    private LocalDate dueOn;

    public LoanResponseDTO() {
    }

    public LoanResponseDTO(Long id, String bookTitle, LocalDate borrowedOn, LocalDate dueOn) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.borrowedOn = borrowedOn;
        this.dueOn = dueOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public LocalDate getBorrowedOn() {
        return borrowedOn;
    }

    public void setBorrowedOn(LocalDate borrowedOn) {
        this.borrowedOn = borrowedOn;
    }

    public LocalDate getDueOn() {
        return dueOn;
    }

    public void setDueOn(LocalDate dueOn) {
        this.dueOn = dueOn;
    }
}
