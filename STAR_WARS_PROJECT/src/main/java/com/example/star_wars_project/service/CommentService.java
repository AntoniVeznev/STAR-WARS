package com.example.star_wars_project.service;

import com.example.star_wars_project.model.binding.CommentAddBindingModel;
import com.example.star_wars_project.model.view.CommentsView;

import java.util.List;

public interface CommentService {
    List<CommentsView> getCommentsByMovieId(Long movieId);

    List<CommentsView> getCommentsBySerialId(Long serialId);

    List<CommentsView> getCommentsByGameId(Long gameId);

    CommentsView createCommentMovie(CommentAddBindingModel commentAddBindingModel, Long movieId, String name);

    CommentsView createCommentSerial(CommentAddBindingModel commentAddBindingModel, Long serialId, String name);

    CommentsView createCommentGame(CommentAddBindingModel commentAddBindingModel, Long gameId, String name);

    CommentsView getCommentById(Long commentId);

    void deleteComment(String name, CommentsView commentsView);
}
