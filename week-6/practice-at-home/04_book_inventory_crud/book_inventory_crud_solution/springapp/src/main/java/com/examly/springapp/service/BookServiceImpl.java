package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.examly.springapp.dto.BookRequestDTO;
import com.examly.springapp.model.Book;
import com.examly.springapp.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
    public Optional<Book> updateBook(Long id, BookRequestDTO request) {
        Optional<Book> existing = bookRepository.findById(id);
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        Book book = existing.get();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setCopies(request.getCopies());
        book.setAvailable(request.getAvailable());
        return Optional.of(bookRepository.save(book));
    }

    @Override
    public boolean deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            return false;
        }
        bookRepository.deleteById(id);
        return true;
    }
}
