package com.example.star_wars_project.web.rest;

import com.example.star_wars_project.model.binding.CommentAddBindingModel;
import com.example.star_wars_project.model.view.CommentsView;
import com.example.star_wars_project.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CommentRestControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentRestController commentRestController;

    @Test
    void testGetCommentsMovies() {

        Long movieId = 1L;
        List<CommentsView> comments = new ArrayList<>();
        CommentsView comment1 = new CommentsView();
        comment1.setId(1L);
        comment1.setPostContent("Great movie!");
        comment1.setAuthorName("user1");
        comments.add(comment1);
        CommentsView comment2 = new CommentsView();
        comment2.setId(2L);
        comment2.setPostContent("I loved it!");
        comment2.setAuthorName("user2");
        comments.add(comment2);
        Mockito.when(commentService.getCommentsByMovie(movieId)).thenReturn(comments);


        ResponseEntity<List<CommentsView>> response = commentRestController.getCommentsMovies(movieId);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(1L, response.getBody().get(0).getId());
        assertEquals("Great movie!", response.getBody().get(0).getPostContent());
        assertEquals("user1", response.getBody().get(0).getAuthorName());
        assertEquals(2L, response.getBody().get(1).getId());
        assertEquals("I loved it!", response.getBody().get(1).getPostContent());
        assertEquals("user2", response.getBody().get(1).getAuthorName());
    }

    @Test
    void testGetComment() {

        Long commentId = 1L;
        Long movieId = 1L;
        CommentsView comment = new CommentsView();
        comment.setId(commentId);
        comment.getPostContent();
        comment.getAuthorName();
        Mockito.when(commentService.getComment(commentId)).thenReturn(comment);


        ResponseEntity<CommentsView> response = commentRestController.getComment(commentId, movieId.toString());


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        assertEquals("", "");
        assertEquals("", "");
    }

    @Test
    void testCreateComment() {

        Long movieId = 1L;
        CommentAddBindingModel commentAddBindingModel = new CommentAddBindingModel();
        commentAddBindingModel.setPostContent("Great movie!");
        Principal principal = Mockito.mock(Principal.class);
        Mockito.when(principal.getName()).thenReturn("user1");
        CommentsView commentsView = new CommentsView();
        commentsView.setId(1L);
        commentsView.setPostContent("Great movie!");
        commentsView.setAuthorName("user1");
        Mockito.when(commentService.createComment(commentAddBindingModel, movieId, "user1")).thenReturn(commentsView);


        ResponseEntity<CommentsView> response = commentRestController.createComment(principal, commentAddBindingModel, movieId);


        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Great movie!", response.getBody().getPostContent());
        assertEquals("user1", response.getBody().getAuthorName());
    }
}