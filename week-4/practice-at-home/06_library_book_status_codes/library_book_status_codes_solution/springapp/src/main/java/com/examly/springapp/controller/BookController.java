package com.examly.springapp.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.dto.*;
import com.examly.springapp.model.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();
    private long nextId = 3;

    public BookController() {
        books.add(new Book(1L, "9780134685991", "Effective Java", "Joshua Bloch", 3));
        books.add(new Book(2L, "9780132350884", "Clean Code", "Robert C. Martin", 5));
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(books.size()))
                .body(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        Book book = null;
        for (Book item : books) {
            if (item.getId().equals(id)) {
                book = item;
                break;
            }
        }
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Book not found with id " + id));
        }
        return ResponseEntity.ok()
                .header("X-Total-Copies", String.valueOf(book.getCopies()))
                .body(book);
    }

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody Book book) {
        if (book.getIsbn() == null || book.getIsbn().isBlank() || book.getTitle() == null || book.getTitle().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "ISBN and title are required"));
        }
        if (book.getCopies() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Copies cannot be negative"));
        }
        for (Book b : books) {
            if (b.getIsbn().equals(book.getIsbn())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDTO(400, "Bad Request", "Book already exists with ISBN " + book.getIsbn()));
            }
        }
        book.setId(nextId++);
        books.add(book);
        return ResponseEntity.created(URI.create("/api/books/" + book.getId())).body(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        Book book = null;
        for (Book item : books) {
            if (item.getId().equals(id)) {
                book = item;
                break;
            }
        }
        if (book == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Book not found with id " + id));
        }
        books.remove(book);
        return ResponseEntity.noContent().build();
    }
}
