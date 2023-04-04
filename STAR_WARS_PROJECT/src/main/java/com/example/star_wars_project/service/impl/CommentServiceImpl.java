package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.binding.CommentAddBindingModel;
import com.example.star_wars_project.model.entity.Comment;
import com.example.star_wars_project.model.view.CommentsView;
import com.example.star_wars_project.repository.CommentRepository;
import com.example.star_wars_project.repository.MovieRepository;
import com.example.star_wars_project.repository.UserRepository;
import com.example.star_wars_project.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, MovieRepository movieRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<CommentsView> getCommentsByMovie(Long movieId) {
        List<Comment> allByMovie = commentRepository.findCommentsByMovie_IdOrderByCreatedDesc(movieId);
        for (Comment comment : allByMovie) {
            System.out.println(comment.getMovie().getTitle());
            System.out.println(comment.getCreated());
        }
        return allByMovie
                .stream()
                .map(comment -> {
                    CommentsView commentsView = modelMapper.map(comment, CommentsView.class);
                    commentsView.setCreated(comment.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy' at 'HH:mm")));

                    return commentsView;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CommentsView createComment(CommentAddBindingModel commentAddBindingModel, Long movieId, String name) {
        if (commentAddBindingModel.getPostContent().equals("")) {
            return null;
        }

        Comment comment = new Comment();
        comment.setCreated(LocalDateTime.now());
        comment.setMovie(movieRepository.findMovieById(movieId));
        comment.setAuthor(userRepository.findUserByUsername(name).orElse(null));
        comment.setPostContent(commentAddBindingModel.getPostContent());
        CommentsView commentsView = modelMapper.map(comment, CommentsView.class);
        commentsView.setCreated(comment.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy' at 'HH:mm h.")));
        commentRepository.save(comment);
        commentsView.setId(comment.getId());
        return commentsView;
    }


    public CommentsView getComment(Long id) {
        Comment comment = commentRepository.findCommentById(id);
        CommentsView commentsView = modelMapper.map(comment, CommentsView.class);
        commentsView.setCreated(comment.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy' at 'HH:mm h.")));
        commentsView.setPostContent(comment.getPostContent());
        return commentsView;
    }


}
