package com.examly.springapp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.examly.springapp.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT DISTINCT x FROM Post x LEFT JOIN FETCH x.comments ORDER BY x.id")
    List<Post> findAllWithComments();
}
