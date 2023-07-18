package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.binding.CommentAddBindingModel;
import com.example.star_wars_project.model.entity.*;
import com.example.star_wars_project.model.view.CommentsView;
import com.example.star_wars_project.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private SeriesRepository seriesRepository;
    @Mock
    private GameRepository gameRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void testGetCommentsByMovie() {

        Long movieId = 1L;

        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setPostContent("Test comment 1");
        comment1.setCreated(LocalDateTime.now().minusDays(1));

        Comment comment2 = new Comment();
        comment2.setId(2L);
        comment2.setPostContent("Test comment 2");
        comment2.setCreated(LocalDateTime.now());

        Movie movie = new Movie();
        movie.setId(movieId);

        List<Comment> comments = Arrays.asList(comment1, comment2);
        when(commentRepository.findCommentsByMovie_IdOrderByCreatedDesc(movieId)).thenReturn(comments);
        when(modelMapper.map(comment1, CommentsView.class)).thenReturn(new CommentsView());
        when(modelMapper.map(comment2, CommentsView.class)).thenReturn(new CommentsView());

        List<CommentsView> commentsByMovie = commentService.getCommentsByMovieId(movieId);

        assertEquals(2, commentsByMovie.size());
        assertEquals(null, commentsByMovie.get(0).getId());
        assertEquals(null, commentsByMovie.get(1).getId());
    }

    @Test
    void testGetCommentsBySerial() {

        Long serialId = 1L;

        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setPostContent("Test comment 1");
        comment1.setCreated(LocalDateTime.now().minusDays(1));

        Comment comment2 = new Comment();
        comment2.setId(2L);
        comment2.setPostContent("Test comment 2");
        comment2.setCreated(LocalDateTime.now());

        Series series = new Series();
        series.setId(serialId);

        List<Comment> comments = Arrays.asList(comment1, comment2);
        when(commentRepository.findCommentsBySeries_IdOrderByCreatedDesc(serialId)).thenReturn(comments);
        when(modelMapper.map(comment1, CommentsView.class)).thenReturn(new CommentsView());
        when(modelMapper.map(comment2, CommentsView.class)).thenReturn(new CommentsView());

        List<CommentsView> commentsBySerial = commentService.getCommentsBySerialId(serialId);

        assertEquals(2, commentsBySerial.size());
        assertEquals(null, commentsBySerial.get(0).getId());
        assertEquals(null, commentsBySerial.get(1).getId());
    }

    @Test
    void testGetCommentsByGame() {

        Long gameId = 1L;

        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setPostContent("Test comment 1");
        comment1.setCreated(LocalDateTime.now().minusDays(1));

        Comment comment2 = new Comment();
        comment2.setId(2L);
        comment2.setPostContent("Test comment 2");
        comment2.setCreated(LocalDateTime.now());

        Game game = new Game();
        game.setId(gameId);

        List<Comment> comments = Arrays.asList(comment1, comment2);
        when(commentRepository.findCommentsByGame_IdOrderByCreatedDesc(gameId)).thenReturn(comments);
        when(modelMapper.map(comment1, CommentsView.class)).thenReturn(new CommentsView());
        when(modelMapper.map(comment2, CommentsView.class)).thenReturn(new CommentsView());

        List<CommentsView> commentsByGame = commentService.getCommentsByGameId(gameId);

        assertEquals(2, commentsByGame.size());
        assertEquals(null, commentsByGame.get(0).getId());
        assertEquals(null, commentsByGame.get(1).getId());
    }

    @Test
    void testCreateCommentMovieWithEmptyPostContent() {
        Long movieId = 1L;
        String username = "testuser";
        String postContent = "";

        CommentAddBindingModel commentAddBindingModel = new CommentAddBindingModel();
        commentAddBindingModel.setPostContent(postContent);

        CommentsView createdComment = commentService.createCommentMovie(commentAddBindingModel, movieId, username);

        assertNull(createdComment);
    }

    @Test
    void testCreateCommentSerialWithEmptyPostContent() {
        Long serialId = 1L;
        String username = "testuser";
        String postContent = "";

        CommentAddBindingModel commentAddBindingModel = new CommentAddBindingModel();
        commentAddBindingModel.setPostContent(postContent);

        CommentsView createdComment = commentService.createCommentSerial(commentAddBindingModel, serialId, username);

        assertNull(createdComment);
    }

    @Test
    void testCreateCommentGameWithEmptyPostContent() {
        Long gameId = 1L;
        String username = "testuser";
        String postContent = "";

        CommentAddBindingModel commentAddBindingModel = new CommentAddBindingModel();
        commentAddBindingModel.setPostContent(postContent);

        CommentsView createdComment = commentService.createCommentGame(commentAddBindingModel, gameId, username);

        assertNull(createdComment);
    }

    @Test
    void createCommentTest() {

        CommentAddBindingModel commentAddBindingModel = new CommentAddBindingModel();
        commentAddBindingModel.setPostContent("This is a test comment");

        Movie movie = new Movie();
        movie.setId(1L);

        User user = new User();
        user.setUsername("testuser");

        Mockito.when(movieRepository.findMovieById(1L)).thenReturn(movie);
        Mockito.when(userRepository.findUserByUsername("testuser")).thenReturn(Optional.of(user));
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new CommentsView());


        CommentsView commentsView = commentService.createCommentMovie(commentAddBindingModel, 1L, "testuser");


        assertEquals(null, commentsView.getPostContent());
    }

    @Test
    void createCommentSeriesTest() {

        CommentAddBindingModel commentAddBindingModel = new CommentAddBindingModel();
        commentAddBindingModel.setPostContent("This is a test comment");

        Series series = new Series();
        series.setId(1L);

        User user = new User();
        user.setUsername("testuser");

        Mockito.when(seriesRepository.findSerialById(1L)).thenReturn(series);
        Mockito.when(userRepository.findUserByUsername("testuser")).thenReturn(Optional.of(user));
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new CommentsView());


        CommentsView commentsView = commentService.createCommentSerial(commentAddBindingModel, 1L, "testuser");


        assertEquals(null, commentsView.getPostContent());
    }

    @Test
    void createCommentGamesTest() {

        CommentAddBindingModel commentAddBindingModel = new CommentAddBindingModel();
        commentAddBindingModel.setPostContent("This is a test comment");

        Game game = new Game();
        game.setId(1L);

        User user = new User();
        user.setUsername("testuser");

        Mockito.when(gameRepository.findGameById(1L)).thenReturn(game);
        Mockito.when(userRepository.findUserByUsername("testuser")).thenReturn(Optional.of(user));
        Mockito.when(modelMapper.map(Mockito.any(), Mockito.any())).thenReturn(new CommentsView());


        CommentsView commentsView = commentService.createCommentGame(commentAddBindingModel, 1L, "testuser");


        assertEquals(null, commentsView.getPostContent());
    }


    @Test
    void testGetComment() {

        Comment comment = new Comment();
        comment.setCreated(LocalDateTime.now());
        comment.setMovie(movieRepository.findMovieById(1L));
        comment.setAuthor(userRepository.findUserByUsername("john").orElse(null));
        comment.setPostContent("This is a test comment");
        commentRepository.save(comment);

    }
}
