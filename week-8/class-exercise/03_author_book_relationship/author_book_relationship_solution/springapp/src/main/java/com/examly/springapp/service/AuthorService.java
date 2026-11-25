package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Author;

public interface AuthorService {

    Author saveAuthor(Author author);

    List<Author> getAllAuthors();

    Optional<Author> getAuthorById(Long id);
}
