package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Author;
import com.examly.springapp.model.Book;

public interface BookService {

    Book saveBook(Book book);

    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);

    boolean hasAuthor(Long bookId, Long authorId);

    Book addAuthor(Long bookId, Long authorId);

    Book removeAuthor(Long bookId, Long authorId);

    void deleteBook(Long id);
}
