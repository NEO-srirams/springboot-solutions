package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Book;
import com.examly.springapp.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getBookByGenre(String genre) {
        return bookRepository.findByGenreIgnoreCaseOrderByPriceDesc(genre);
    }

    @Override
    public List<Book> searchBook(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCase(keyword);
    }

    @Override
    public List<Book> getBookByPriceRange(Double min, Double max) {
        return bookRepository.findByPriceBetween(min, max);
    }

    @Override
    public List<Book> filterBook(String genre, Double min) {
        return bookRepository.findByGenreIgnoreCaseAndPriceGreaterThanEqual(genre, min);
    }

    @Override
    public List<Book> eitherBook(String genre, String keyword) {
        return bookRepository.findByGenreIgnoreCaseOrTitleContainingIgnoreCase(genre, keyword);
    }

    @Override
    public List<Book> getBookAbovePrice(Double min) {
        return bookRepository.findAbovePrice(min);
    }

    @Override
    public List<Book> getBookUpToPrice(Double max) {
        return bookRepository.findUpToPriceNative(max);
    }

    @Override
    public int updateStatusByGenre(String genre, String status) {
        return bookRepository.updateStatusByGenre(genre, status);
    }

    @Override
    public Page<Book> getBookPage(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return bookRepository.findAll(PageRequest.of(page, size, sort));
    }
}
