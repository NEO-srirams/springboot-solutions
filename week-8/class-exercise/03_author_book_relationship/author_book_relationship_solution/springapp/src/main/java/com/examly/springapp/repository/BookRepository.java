package com.examly.springapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAuthorId(Long authorId);
}
