package com.examly.springapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.Book;
import com.examly.springapp.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Book>> getBookByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(bookService.getBookByGenre(genre));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBook(@RequestParam String keyword) {
        return ResponseEntity.ok(bookService.searchBook(keyword));
    }

    @GetMapping("/price-range")
    public ResponseEntity<?> getBookByPriceRange(@RequestParam Double min, @RequestParam Double max) {
        if (min > max) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Minimum must not be greater than maximum"));
        }
        return ResponseEntity.ok(bookService.getBookByPriceRange(min, max));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Book>> filterBook(@RequestParam String genre, @RequestParam Double minPrice) {
        return ResponseEntity.ok(bookService.filterBook(genre, minPrice));
    }

    @GetMapping("/either")
    public ResponseEntity<List<Book>> eitherBook(@RequestParam String genre, @RequestParam String keyword) {
        return ResponseEntity.ok(bookService.eitherBook(genre, keyword));
    }

    @GetMapping("/above")
    public ResponseEntity<List<Book>> getBookAbovePrice(@RequestParam Double minPrice) {
        return ResponseEntity.ok(bookService.getBookAbovePrice(minPrice));
    }

    @GetMapping("/native")
    public ResponseEntity<List<Book>> getBookUpToPrice(@RequestParam Double maxPrice) {
        return ResponseEntity.ok(bookService.getBookUpToPrice(maxPrice));
    }

    @PatchMapping("/status")
    public ResponseEntity<?> updateStatusByGenre(@RequestParam String genre, @RequestParam String status) {
        if (status == null || status.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Status is required"));
        }
        return ResponseEntity.ok(Map.of("updated", bookService.updateStatusByGenre(genre, status)));
    }

    @GetMapping("/page")
    public ResponseEntity<?> getBookPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "asc") String direction) {
        if (page < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Page must not be negative"));
        }
        if (size < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Size must be at least 1"));
        }
        Page<Book> result = bookService.getBookPage(page, size, sortBy, direction);
        return ResponseEntity.ok(result);
    }
}
