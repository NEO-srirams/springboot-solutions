package com.examly.springapp.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.examly.springapp.model.Book;

public interface BookService {

    List<Book> getBookByGenre(String genre);

    List<Book> searchBook(String keyword);

    List<Book> getBookByPriceRange(Double min, Double max);

    List<Book> filterBook(String genre, Double min);

    List<Book> eitherBook(String genre, String keyword);

    List<Book> getBookAbovePrice(Double min);

    List<Book> getBookUpToPrice(Double max);

    int updateStatusByGenre(String genre, String status);

    Page<Book> getBookPage(int page, int size, String sortBy, String direction);
}
