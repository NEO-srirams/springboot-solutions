package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByGenreIgnoreCaseOrderByPriceDesc(String genre);

    List<Book> findByTitleContainingIgnoreCase(String keyword);

    List<Book> findByPriceBetween(Double min, Double max);

    List<Book> findByGenreIgnoreCaseAndPriceGreaterThanEqual(String genre, Double min);

    List<Book> findByGenreIgnoreCaseOrTitleContainingIgnoreCase(String genre, String keyword);

    @Query("SELECT x FROM Book x WHERE x.price > :min ORDER BY x.price DESC")
    List<Book> findAbovePrice(@Param("min") Double min);

    @Query(value = "SELECT * FROM books WHERE price <= :max ORDER BY price ASC", nativeQuery = true)
    List<Book> findUpToPriceNative(@Param("max") Double max);

    @Modifying
    @Transactional
    @Query("UPDATE Book x SET x.status = :status WHERE LOWER(x.genre) = LOWER(:genre)")
    int updateStatusByGenre(@Param("genre") String genre, @Param("status") String status);
}
