package com.examly.springapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examly.springapp.model.Comment;
import com.examly.springapp.model.Post;
import com.examly.springapp.repository.CommentRepository;
import com.examly.springapp.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAllWithComments();
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Transactional
    @Override
    public Optional<Comment> addComment(Long postId, Comment comment) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return Optional.empty();
        }
        comment.setId(null);
        post.get().addComment(comment);
        return Optional.of(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public boolean removeComment(Long postId, Long commentId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isEmpty()) {
            return false;
        }
        for (Comment x : post.get().getComments()) {
            if (x.getId().equals(commentId)) {
                post.get().removeComment(x);
                postRepository.save(post.get());
                return true;
            }
        }
        return false;
    }

    @Transactional
    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
