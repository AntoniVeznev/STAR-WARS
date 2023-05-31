package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.binding.CommentAddBindingModel;
import com.example.star_wars_project.model.entity.Comment;
import com.example.star_wars_project.model.view.CommentsView;
import com.example.star_wars_project.repository.*;
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
    private final SeriesRepository seriesRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, MovieRepository movieRepository, SeriesRepository seriesRepository, GameRepository gameRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.movieRepository = movieRepository;
        this.seriesRepository = seriesRepository;
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public List<CommentsView> getCommentsByMovieId(Long movieId) {
        List<Comment> allByMovie = commentRepository.findCommentsByMovie_IdOrderByCreatedDesc(movieId);
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
    public List<CommentsView> getCommentsBySerialId(Long serialId) {
        List<Comment> allBySerial = commentRepository.findCommentsBySeries_IdOrderByCreatedDesc(serialId);
        return allBySerial
                .stream()
                .map(comment -> {
                    CommentsView commentsView = modelMapper.map(comment, CommentsView.class);
                    commentsView.setCreated(comment.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy' at 'HH:mm")));
                    return commentsView;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentsView> getCommentsByGameId(Long gameId) {
        List<Comment> allByGame = commentRepository.findCommentsByGame_IdOrderByCreatedDesc(gameId);
        return allByGame
                .stream()
                .map(comment -> {
                    CommentsView commentsView = modelMapper.map(comment, CommentsView.class);
                    commentsView.setCreated(comment.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy' at 'HH:mm")));
                    return commentsView;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CommentsView createCommentMovie(CommentAddBindingModel commentAddBindingModel, Long movieId, String name) {
        if (commentAddBindingModel.getPostContent().equals("")) {
            return null;
        }

        Comment comment = new Comment();
        comment.setCreated(LocalDateTime.now());
        comment.setMovie(movieRepository.findMovieById(movieId));
        comment.setAuthor(userRepository.findUserByUsername(name).orElse(null));
        comment.setPostContent(commentAddBindingModel.getPostContent());
        CommentsView commentsView = modelMapper.map(comment, CommentsView.class);
        commentsView.setCreated(comment.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy' at 'HH:mm")));
        commentRepository.save(comment);
        commentsView.setId(comment.getId());
        return commentsView;
    }

    @Override
    public CommentsView createCommentSerial(CommentAddBindingModel commentAddBindingModel, Long serialId, String name) {
        if (commentAddBindingModel.getPostContent().equals("")) {
            return null;
        }

        Comment comment = new Comment();
        comment.setCreated(LocalDateTime.now());
        comment.setSeries(seriesRepository.findSerialById(serialId));
        comment.setAuthor(userRepository.findUserByUsername(name).orElse(null));
        comment.setPostContent(commentAddBindingModel.getPostContent());
        CommentsView commentsView = modelMapper.map(comment, CommentsView.class);
        commentsView.setCreated(comment.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy' at 'HH:mm")));
        commentRepository.save(comment);
        commentsView.setId(comment.getId());
        return commentsView;
    }

    @Override
    public CommentsView createCommentGame(CommentAddBindingModel commentAddBindingModel, Long gameId, String name) {
        if (commentAddBindingModel.getPostContent().equals("")) {
            return null;
        }

        Comment comment = new Comment();
        comment.setCreated(LocalDateTime.now());
        comment.setGame(gameRepository.findGameById(gameId));
        comment.setAuthor(userRepository.findUserByUsername(name).orElse(null));
        comment.setPostContent(commentAddBindingModel.getPostContent());
        CommentsView commentsView = modelMapper.map(comment, CommentsView.class);
        commentsView.setCreated(comment.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy' at 'HH:mm")));
        commentRepository.save(comment);
        commentsView.setId(comment.getId());
        return commentsView;
    }

    @Override
    public CommentsView getCommentById(Long commentId) {
        Comment comment = commentRepository.findCommentById(commentId);
        CommentsView commentsView = modelMapper.map(comment, CommentsView.class);
        commentsView.setCreated(comment.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy' at 'HH:mm")));
        commentsView.setPostContent(comment.getPostContent());
        return commentsView;
    }
}