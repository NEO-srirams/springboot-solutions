package com.examly.springapp.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.dto.CommentRequestDTO;
import com.examly.springapp.dto.CommentResponseDTO;
import com.examly.springapp.dto.ErrorResponseDTO;
import com.examly.springapp.dto.PostRequestDTO;
import com.examly.springapp.dto.PostResponseDTO;
import com.examly.springapp.model.Comment;
import com.examly.springapp.model.Post;
import com.examly.springapp.service.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequestDTO request) {
        if (request.getTitle() == null || request.getTitle().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Title is required"));
        }
        if (request.getAuthor() == null || request.getAuthor().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Author is required"));
        }
        if (request.getContent() == null || request.getContent().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Content is required"));
        }
        if (request.getComments() != null) {
            for (CommentRequestDTO item : request.getComments()) {
            if (item.getCommenter() == null || item.getCommenter().isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDTO(400, "Bad Request", "Comment commenter is required"));
            }
            if (item.getText() == null || item.getText().isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponseDTO(400, "Bad Request", "Comment text is required"));
            }
            }
        }
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setAuthor(request.getAuthor());
        post.setContent(request.getContent());
        if (request.getComments() != null) {
            for (CommentRequestDTO item : request.getComments()) {
                Comment comment = new Comment();
                comment.setCommenter(item.getCommenter());
                comment.setText(item.getText());
                post.addComment(comment);
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(toPostResponse(postService.savePost(post)));
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        List<PostResponseDTO> result = new ArrayList<>();
        for (Post x : postService.getAllPosts()) {
            result.add(toPostResponse(x));
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            return ResponseEntity.ok(toPostResponse(post.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Post not found with id " + id));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> addCommentToPost(@PathVariable Long id, @RequestBody CommentRequestDTO item) {
        if (item.getCommenter() == null || item.getCommenter().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Commenter is required"));
        }
        if (item.getText() == null || item.getText().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponseDTO(400, "Bad Request", "Text is required"));
        }
        Comment comment = new Comment();
        comment.setCommenter(item.getCommenter());
        comment.setText(item.getText());
        Optional<Comment> saved = postService.addComment(id, comment);
        if (saved.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(toCommentResponse(saved.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Post not found with id " + id));
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<?> removeCommentFromPost(@PathVariable Long id, @PathVariable Long commentId) {
        if (postService.getPostById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Post not found with id " + id));
        }
        if (postService.removeComment(id, commentId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponseDTO(404, "Not Found", "Comment not found with id " + commentId + " in Post " + id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        if (postService.getPostById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponseDTO(404, "Not Found", "Post not found with id " + id));
        }
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    private CommentResponseDTO toCommentResponse(Comment x) {
        return new CommentResponseDTO(x.getId(), x.getCommenter(), x.getText());
    }

    private PostResponseDTO toPostResponse(Post x) {
        List<CommentResponseDTO> items = new ArrayList<>();
        for (Comment y : x.getComments()) {
            items.add(toCommentResponse(y));
        }
        items.sort(Comparator.comparing(CommentResponseDTO::getId));
        return new PostResponseDTO(x.getId(), x.getTitle(), x.getAuthor(), x.getContent(), items);
    }
}
