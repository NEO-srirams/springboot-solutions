package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import com.examly.springapp.model.Comment;
import com.examly.springapp.model.Post;

public interface PostService {

    Post savePost(Post post);

    List<Post> getAllPosts();

    Optional<Post> getPostById(Long id);

    Optional<Comment> addComment(Long postId, Comment comment);

    boolean removeComment(Long postId, Long commentId);

    void deletePost(Long id);
}
