package com.examly.springapp.service;

import java.util.Optional;

import com.examly.springapp.model.Author;

public interface AuthorService {

    Author saveAuthor(Author author);

    Optional<Author> getAuthorById(Long id);
}
