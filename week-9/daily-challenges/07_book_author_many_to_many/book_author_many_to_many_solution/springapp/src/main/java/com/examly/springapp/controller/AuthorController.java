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

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = authorService.getAuthorById(id);
        if (author.isPresent()) {
            return ResponseEntity.ok(toAuthorResponse(author.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Author not found with id " + id));
    }

    private AuthorResponseDTO toAuthorResponse(Author x) {
        List<BookSummaryDTO> list = new ArrayList<>();
        for (Book y : x.getBooks()) {
            list.add(new BookSummaryDTO(y.getId(), y.getTitle()));
        }
        list.sort(Comparator.comparing(BookSummaryDTO::getId));
        return new AuthorResponseDTO(x.getId(), x.getName(), x.getCountry(), list);
    }
}
