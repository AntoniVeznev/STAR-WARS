package com.example.star_wars_project.web.rest;

import com.example.star_wars_project.exception.ItemNotFoundException;
import com.example.star_wars_project.model.binding.CommentAddBindingModel;
import com.example.star_wars_project.model.view.CommentsView;
import com.example.star_wars_project.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
public class CommentRestController {
    private final CommentService commentService;

    public CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/api/movies/comments/{movieId}")
    public ResponseEntity<List<CommentsView>> getAllCommentsForThisMovie(@PathVariable("movieId") Long movieId) {
        List<CommentsView> commentsByMovie = commentService.getCommentsByMovieId(movieId);
        if (commentsByMovie.isEmpty()) {
            throw new ItemNotFoundException();
        }
        return ResponseEntity.ok(commentsByMovie);
    }

    @GetMapping("/api/movie/{movieId}/comments/{commentId}")
    public ResponseEntity<CommentsView> getCommentMovie(@PathVariable("commentId") Long commentId, @PathVariable String movieId) {
        CommentsView commentsView = commentService.getCommentById(commentId);
        return ResponseEntity.ok(commentsView);
    }

    @PostMapping(value = "/api/movie/{movieId}/comments", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentsView> createMovieComment(Principal principal,
                                                           @RequestBody CommentAddBindingModel commentAddBindingModel,
                                                           @PathVariable("movieId") Long movieId) {
        String name = principal.getName();
        CommentsView commentsView = commentService.createCommentMovie(commentAddBindingModel, movieId, name);

        if (commentsView == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.created(URI.create(String.format("/api/movie/%d/comments/%d", movieId, commentsView.getId()))).body(commentsView);
    }

    @DeleteMapping("/api/movie/{movieId}/comments/{commentId}")
    public ResponseEntity<CommentsView> deleteMovieCommentById(Principal principal,
                                                               @PathVariable("commentId") Long commentId,
                                                               @PathVariable String movieId) {
        String name = principal.getName();
        CommentsView commentsView = commentService.getCommentById(commentId);

        if (commentsView != null) {
            commentService.deleteComment(name, commentsView);
            return ResponseEntity.ok(commentsView);
        }
        return ResponseEntity.status(403).build();
    }

    @GetMapping("/api/series/comments/{serialId}")
    public ResponseEntity<List<CommentsView>> getAllCommentsForThisSeries(@PathVariable("serialId") Long serialId) {
        List<CommentsView> commentsBySerial = commentService.getCommentsBySerialId(serialId);
        if (commentsBySerial.isEmpty()) {
            throw new ItemNotFoundException();
        }
        return ResponseEntity.ok(commentsBySerial);
    }

    @GetMapping("/api/serial/{serialId}/comments/{commentId}")
    public ResponseEntity<CommentsView> getCommentSerial(@PathVariable("commentId") Long commentId, @PathVariable String serialId) {
        CommentsView commentsView = commentService.getCommentById(commentId);
        return ResponseEntity.ok(commentsView);
    }

    @PostMapping(value = "/api/serial/{serialId}/comments", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentsView> createSerialComment(Principal principal,
                                                            @RequestBody CommentAddBindingModel commentAddBindingModel,
                                                            @PathVariable("serialId") Long serialId) {
        String name = principal.getName();
        CommentsView commentsView = commentService.createCommentSerial(commentAddBindingModel, serialId, name);
        if (commentsView == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.created(URI.create(String.format("/api/serial/%d/comments/%d", serialId, commentsView.getId()))).body(commentsView);
    }

    @DeleteMapping("/api/serial/{serialId}/comments/{commentId}")
    public ResponseEntity<CommentsView> deleteSerialCommentById(Principal principal,
                                                                @PathVariable("commentId") Long commentId,
                                                                @PathVariable String serialId) {
        String name = principal.getName();
        CommentsView commentsView = commentService.getCommentById(commentId);

        if (commentsView != null) {
            commentService.deleteComment(name, commentsView);
            return ResponseEntity.ok(commentsView);
        }
        return ResponseEntity.status(403).build();
    }

    @GetMapping("/api/games/comments/{gameId}")
    public ResponseEntity<List<CommentsView>> getAllCommentsForThisGame(@PathVariable("gameId") Long gameId) {
        List<CommentsView> commentsByGame = commentService.getCommentsByGameId(gameId);
        if (commentsByGame.isEmpty()) {
            throw new ItemNotFoundException();
        }
        return ResponseEntity.ok(commentsByGame);
    }


    @GetMapping("/api/game/{gameId}/comments/{commentId}")
    public ResponseEntity<CommentsView> getCommentGame(@PathVariable("commentId") Long commentId, @PathVariable String gameId) {
        CommentsView commentsView = commentService.getCommentById(commentId);
        return ResponseEntity.ok(commentsView);
    }


    @PostMapping(value = "/api/game/{gameId}/comments", consumes = "application/json", produces = "application/json")
    public ResponseEntity<CommentsView> createGameComment(Principal principal,
                                                          @RequestBody CommentAddBindingModel commentAddBindingModel,
                                                          @PathVariable("gameId") Long gameId) {
        String name = principal.getName();
        CommentsView commentsView = commentService.createCommentGame(commentAddBindingModel, gameId, name);
        if (commentsView == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.created(URI.create(String.format("/api/game/%d/comments/%d", gameId, commentsView.getId()))).body(commentsView);
    }

    @DeleteMapping("/api/game/{gameId}/comments/{commentId}")
    public ResponseEntity<CommentsView> deleteGameCommentById(Principal principal,
                                                              @PathVariable("commentId") Long commentId,
                                                              @PathVariable String gameId) {
        String name = principal.getName();
        CommentsView commentsView = commentService.getCommentById(commentId);

        if (commentsView != null) {
            commentService.deleteComment(name, commentsView);
            return ResponseEntity.ok(commentsView);
        }
        return ResponseEntity.status(403).build();
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException.class)
    public ModelAndView commentNotFoundException(ItemNotFoundException infe) {
        return new ModelAndView("other-errors/comment-not-found");
    }
}
