package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.Author;
import com.examly.springapp.model.Book;
import com.examly.springapp.repository.AuthorRepository;
import com.examly.springapp.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAllWithAuthors();
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    @Override
    public boolean hasAuthor(Long bookId, Long authorId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        return book.getAuthors().stream().anyMatch(x -> x.getId().equals(authorId));
    }

    @Transactional
    @Override
    public Book addAuthor(Long bookId, Long authorId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        Author author = authorRepository.findById(authorId).orElseThrow();
        book.addAuthor(author);
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book removeAuthor(Long bookId, Long authorId) {
        Book book = bookRepository.findById(bookId).orElseThrow();
        Author author = authorRepository.findById(authorId).orElseThrow();
        book.removeAuthor(author);
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
