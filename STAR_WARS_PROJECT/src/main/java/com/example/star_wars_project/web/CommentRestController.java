package com.example.star_wars_project.web;

import com.example.star_wars_project.model.binding.CommentAddBindingModel;

import com.example.star_wars_project.model.view.CommentsView;
import com.example.star_wars_project.repository.UserRepository;
import com.example.star_wars_project.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
public class CommentRestController {
    private final CommentService commentService;


    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/api/{movieId}/comments")
    public ResponseEntity<List<CommentsView>> getCommentsMovies(@PathVariable("movieId") Long movieId) {
        List<CommentsView> commentsByMovie = commentService.getCommentsByMovie(movieId);
        return ResponseEntity.ok(commentsByMovie);
    }

    @GetMapping("/api/{movieId}/comments/{commentId}")
    public ResponseEntity<CommentsView> getComment(@PathVariable("commentId") Long commentId, @PathVariable String movieId) {
        CommentsView commentsView = commentService.getComment(commentId);
        return ResponseEntity.ok(commentsView);
    }

    @PostMapping(value = "/api/{movieId}/comments", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentsView> createComment(Principal principal,
                                                      @RequestBody CommentAddBindingModel commentAddBindingModel,
                                                      @PathVariable("movieId") Long movieId) {
        String name = principal.getName();
        CommentsView commentsView = commentService.createComment(commentAddBindingModel, movieId, name);
        if (commentsView == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.created(URI.create(String.format("/api/%d/comments/%d", movieId, commentsView.getId()))).body(commentsView);
    }
}

