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
    public ResponseEntity<List<CommentsView>> getMovieComments(@PathVariable("movieId") Long movieId) {
        List<CommentsView> commentsByMovie = commentService.getCommentsByMovieId(movieId);
        return ResponseEntity.ok(commentsByMovie);
    }

    @GetMapping("/api/{serialId}/comment")
    public ResponseEntity<List<CommentsView>> getSerialComments(@PathVariable("serialId") Long serialId) {
        List<CommentsView> commentsBySerial = commentService.getCommentsBySerialId(serialId);
        return ResponseEntity.ok(commentsBySerial);
    }
    @GetMapping("/api/{gameId}/commentss")
    public ResponseEntity<List<CommentsView>> getGameComments(@PathVariable("gameId") Long gameId) {
        List<CommentsView> commentsByGame = commentService.getCommentsByGameId(gameId);
        return ResponseEntity.ok(commentsByGame);
    }

    @GetMapping("/api/{movieId}/comments/{commentId}")
    public ResponseEntity<CommentsView> getCommentMovie(@PathVariable("commentId") Long commentId, @PathVariable String movieId) {
        CommentsView commentsView = commentService.getCommentById(commentId);
        return ResponseEntity.ok(commentsView);
    }

    @GetMapping("/api/{serialId}/comment/{commentId}")
    public ResponseEntity<CommentsView> getCommentSerial(@PathVariable("commentId") Long commentId, @PathVariable String serialId) {
        CommentsView commentsView = commentService.getCommentById(commentId);
        return ResponseEntity.ok(commentsView);
    }
    @GetMapping("/api/{gameId}/commentss/{commentId}")
    public ResponseEntity<CommentsView> getCommentGame(@PathVariable("commentId") Long commentId, @PathVariable String gameId) {
        CommentsView commentsView = commentService.getCommentById(commentId);
        return ResponseEntity.ok(commentsView);
    }


    @PostMapping(value = "/api/{movieId}/comments", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentsView> createMovieComment(Principal principal,
                                                           @RequestBody CommentAddBindingModel commentAddBindingModel,
                                                           @PathVariable("movieId") Long movieId) {
        String name = principal.getName();
        CommentsView commentsView = commentService.createCommentMovie(commentAddBindingModel, movieId, name);
        if (commentsView == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.created(URI.create(String.format("/api/%d/comments/%d", movieId, commentsView.getId()))).body(commentsView);
    }

    @PostMapping(value = "/api/{serialId}/comment", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentsView> createSerialComment(Principal principal,
                                                            @RequestBody CommentAddBindingModel commentAddBindingModel,
                                                            @PathVariable("serialId") Long serialId) {
        String name = principal.getName();
        CommentsView commentsView = commentService.createCommentSerial(commentAddBindingModel, serialId, name);
        if (commentsView == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.created(URI.create(String.format("/api/%d/comment/%d", serialId, commentsView.getId()))).body(commentsView);
    }


    @PostMapping(value = "/api/{gameId}/commentss", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentsView> createGameComment(Principal principal,
                                                           @RequestBody CommentAddBindingModel commentAddBindingModel,
                                                           @PathVariable("gameId") Long gameId) {
        String name = principal.getName();
        CommentsView commentsView = commentService.createCommentGame(commentAddBindingModel, gameId, name);
        if (commentsView == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.created(URI.create(String.format("/api/%d/commentss/%d", gameId, commentsView.getId()))).body(commentsView);
    }

}