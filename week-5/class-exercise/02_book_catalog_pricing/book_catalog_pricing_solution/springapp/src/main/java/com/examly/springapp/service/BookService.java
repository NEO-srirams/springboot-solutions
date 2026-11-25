package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Book;

public interface BookService {

    Book saveBook(Book book);

    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);
}
