package com.example.star_wars_project.service;

import com.example.star_wars_project.model.binding.CommentAddBindingModel;
import com.example.star_wars_project.model.view.CommentsView;

import java.util.List;

public interface CommentService {
    List<CommentsView> getCommentsByMovie(Long movieId);

    CommentsView createComment(CommentAddBindingModel commentAddBindingModel, Long movieId, String name);

    CommentsView getComment(Long commentId);
}