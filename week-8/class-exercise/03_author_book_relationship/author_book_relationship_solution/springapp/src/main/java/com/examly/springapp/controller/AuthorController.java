package com.examly.springapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.AuthorResponseDTO;
import com.examly.springapp.dto.BookResponseDTO;
import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.model.Author;
import com.examly.springapp.model.Book;
import com.examly.springapp.service.AuthorService;
import com.examly.springapp.service.BookService;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;

    public AuthorController(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody Author author) {
        if (author.getName() == null || author.getName().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Name is required"));
        }
        if (author.getCountry() == null || author.getCountry().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Country is required"));
        }
        author.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(toAuthorResponse(authorService.saveAuthor(author)));
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> getAllAuthors() {
        List<AuthorResponseDTO> result = new ArrayList<>();
        for (Author x : authorService.getAllAuthors()) {
            result.add(toAuthorResponse(x));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = authorService.getAuthorById(id);
        if (author.isPresent()) {
            return ResponseEntity.ok(toAuthorResponse(author.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Author not found with id " + id));
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<?> getBooksByAuthor(@PathVariable Long id) {
        if (authorService.getAuthorById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Author not found with id " + id));
        }
        List<BookResponseDTO> result = new ArrayList<>();
        for (Book x : bookService.getBooksByAuthorId(id)) {
            result.add(toBookResponse(x));
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/books")
    public ResponseEntity<?> createBookForAuthor(@PathVariable Long id, @RequestBody Book book) {
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
        Optional<Book> saved = bookService.createBookForAuthor(id, book);
        if (saved.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(toBookResponse(saved.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Author not found with id " + id));
    }

    private AuthorResponseDTO toAuthorResponse(Author x) {
        return new AuthorResponseDTO(x.getId(), x.getName(), x.getCountry(), x.getBooks().size());
    }

    private BookResponseDTO toBookResponse(Book x) {
        return new BookResponseDTO(x.getId(), x.getTitle(), x.getIsbn(), x.getPrice(), x.getAuthor().getId(), x.getAuthor().getName());
    }
}
