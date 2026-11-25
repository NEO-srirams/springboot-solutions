package com.examly.springapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT DISTINCT x FROM Movie x LEFT JOIN FETCH x.actors ORDER BY x.id")
    List<Movie> findAllWithActors();
}
