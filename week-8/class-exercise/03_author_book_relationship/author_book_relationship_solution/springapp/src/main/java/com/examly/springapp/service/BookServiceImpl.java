package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.model.Book;
import com.examly.springapp.model.Author;
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
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> getBooksByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    @Override
    public Optional<Book> createBookForAuthor(Long authorId, Book book) {
        Optional<Author> author = authorRepository.findById(authorId);
        if (author.isEmpty()) {
            return Optional.empty();
        }
        book.setId(null);
        author.get().addBook(book);
        return Optional.of(bookRepository.save(book));
    }
}
