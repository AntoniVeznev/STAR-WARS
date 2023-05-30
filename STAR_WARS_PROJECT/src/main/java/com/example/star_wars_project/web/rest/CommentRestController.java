package com.example.star_wars_project.web.rest;

import com.example.star_wars_project.model.binding.CommentAddBindingModel;
import com.example.star_wars_project.model.view.CommentsView;
import com.example.star_wars_project.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/{serialId}/comment")
    public ResponseEntity<List<CommentsView>> getCommentsSerial(@PathVariable("serialId") Long serialId) {
        List<CommentsView> commentsBySerial = commentService.getCommentsBySerial(serialId);
        return ResponseEntity.ok(commentsBySerial);
    }

    @GetMapping("/api/{movieId}/comments/{commentId}")
    public ResponseEntity<CommentsView> getComment(@PathVariable("commentId") Long commentId, @PathVariable String movieId) {
        CommentsView commentsView = commentService.getComment(commentId);
        return ResponseEntity.ok(commentsView);
    }

    @GetMapping("/api/{serialId}/comment/{commentId}")
    public ResponseEntity<CommentsView> getCommentSerial(@PathVariable("commentId") Long commentId, @PathVariable String serialId) {
        CommentsView commentsView = commentService.getCommentSerial(commentId);
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

    @PostMapping(value = "/api/{serialId}/comment", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentsView> createCommentSerial(Principal principal,
                                                            @RequestBody CommentAddBindingModel commentAddBindingModel,
                                                            @PathVariable("serialId") Long serialId) {

        String name = principal.getName();

        CommentsView commentsView = commentService.createCommentSerial(commentAddBindingModel, serialId, name);

        if (commentsView == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.created(URI.create(String.format("/api/%d/comment/%d", serialId, commentsView.getId()))).body(commentsView);
    }
}