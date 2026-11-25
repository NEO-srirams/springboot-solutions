package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
