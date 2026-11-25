package com.examly.springapp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByGenreIgnoreCaseOrderByRatingDesc(String genre);

    List<Movie> findByTitleContainingIgnoreCase(String keyword);

    List<Movie> findByRatingBetween(Double min, Double max);

    List<Movie> findByGenreIgnoreCaseAndRatingGreaterThanEqual(String genre, Double min);

    List<Movie> findByGenreIgnoreCaseOrTitleContainingIgnoreCase(String genre, String keyword);

    @Query("SELECT x FROM Movie x WHERE x.rating > :min ORDER BY x.rating DESC")
    List<Movie> findAboveRating(@Param("min") Double min);

    @Query(value = "SELECT * FROM movies WHERE rating <= :max ORDER BY rating ASC", nativeQuery = true)
    List<Movie> findUpToRatingNative(@Param("max") Double max);

    @Modifying
    @Transactional
    @Query("UPDATE Movie x SET x.status = :status WHERE LOWER(x.genre) = LOWER(:genre)")
    int updateStatusByGenre(@Param("genre") String genre, @Param("status") String status);
}
