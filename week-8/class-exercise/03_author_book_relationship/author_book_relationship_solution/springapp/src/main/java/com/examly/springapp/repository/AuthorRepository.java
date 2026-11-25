package com.examly.springapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
