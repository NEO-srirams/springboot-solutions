package com.examly.springapp.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.AuthorResponseDTO;
import com.examly.springapp.dto.AuthorSummaryDTO;
import com.examly.springapp.dto.BookResponseDTO;
import com.examly.springapp.dto.BookSummaryDTO;
import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.Author;
import com.examly.springapp.model.Book;
import com.examly.springapp.service.AuthorService;
import com.examly.springapp.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        if (book.getTitle() == null || book.getTitle().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Title is required"));
        }
        if (book.getIsbn() == null || book.getIsbn().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Isbn is required"));
        }
        if (book.getPrice() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Price is required"));
        }
        book.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toBookResponse(bookService.saveBook(book)));
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<BookResponseDTO> result = new ArrayList<>();
        for (Book x : bookService.getAllBooks()) {
            result.add(toBookResponse(x));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(toBookResponse(book.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Book not found with id " + id));
    }

    @PostMapping("/{id}/authors/{authorId}")
    public ResponseEntity<?> addAuthorToBook(@PathVariable Long id, @PathVariable Long authorId) {
        if (bookService.getBookById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Book not found with id " + id));
        }
        if (authorService.getAuthorById(authorId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Author not found with id " + authorId));
        }
        if (bookService.hasAuthor(id, authorId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Book already has this author"));
        }
        return ResponseEntity.ok(toBookResponse(bookService.addAuthor(id, authorId)));
    }

    @DeleteMapping("/{id}/authors/{authorId}")
    public ResponseEntity<?> removeAuthorFromBook(@PathVariable Long id, @PathVariable Long authorId) {
        if (bookService.getBookById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Book not found with id " + id));
        }
        if (authorService.getAuthorById(authorId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Author not found with id " + authorId));
        }
        if (!bookService.hasAuthor(id, authorId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Book does not have this author"));
        }
        bookService.removeAuthor(id, authorId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        if (bookService.getBookById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Book not found with id " + id));
        }
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    private BookResponseDTO toBookResponse(Book x) {
        List<AuthorSummaryDTO> list = new ArrayList<>();
        for (Author y : x.getAuthors()) {
            list.add(new AuthorSummaryDTO(y.getId(), y.getName()));
        }
        list.sort(Comparator.comparing(AuthorSummaryDTO::getId));
        return new BookResponseDTO(x.getId(), x.getTitle(), x.getIsbn(), x.getPrice(), list);
    }
}
