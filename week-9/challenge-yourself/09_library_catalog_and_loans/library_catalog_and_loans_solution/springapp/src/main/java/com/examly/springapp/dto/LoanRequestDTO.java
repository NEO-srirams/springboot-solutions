package com.examly.springapp.dto;

import java.time.LocalDate;

public class LoanRequestDTO {

    private String bookTitle;
    private LocalDate borrowedOn;
    private LocalDate dueOn;

    public LoanRequestDTO() {
    }

    public LoanRequestDTO(String bookTitle, LocalDate borrowedOn, LocalDate dueOn) {
        this.bookTitle = bookTitle;
        this.borrowedOn = borrowedOn;
        this.dueOn = dueOn;
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
