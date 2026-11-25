package com.examly.springapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT DISTINCT x FROM Book x LEFT JOIN FETCH x.authors ORDER BY x.id")
    List<Book> findAllWithAuthors();
}
