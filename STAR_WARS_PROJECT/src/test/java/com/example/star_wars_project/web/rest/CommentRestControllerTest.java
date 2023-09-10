package com.example.star_wars_project.web.rest;

import com.example.star_wars_project.model.binding.CommentAddBindingModel;
import com.example.star_wars_project.model.entity.*;
import com.example.star_wars_project.model.view.CommentsView;
import com.example.star_wars_project.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentService commentService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllCommentsForMovie() throws Exception {
        when(commentService.getCommentsByMovieId(1L))
                .thenReturn(List.of(createComment("text1"), createComment("text2")));
        mockMvc
                .perform(get("/api/movies/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].postContent", is("text1")))
                .andExpect(jsonPath("$.[1].postContent", is("text2")));
    }

    @Test
    void getAllCommentsForSerial() throws Exception {
        when(commentService.getCommentsBySerialId(1L))
                .thenReturn(List.of(createComment("text1"), createComment("text2")));
        mockMvc
                .perform(get("/api/series/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].postContent", is("text1")))
                .andExpect(jsonPath("$.[1].postContent", is("text2")));
    }

    @Test
    void getAllCommentsForGame() throws Exception {
        when(commentService.getCommentsByGameId(1L))
                .thenReturn(List.of(createComment("text1"), createComment("text2")));
        mockMvc
                .perform(get("/api/games/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].postContent", is("text1")))
                .andExpect(jsonPath("$.[1].postContent", is("text2")));
    }

    @Test
    void getCommentByMovieIDAndCommentId() throws Exception {
        when(commentService.getCommentById(1L))
                .thenReturn(createComment("some text here!"));
        mockMvc
                .perform(get("/api/movie/1/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postContent", is("some text here!")));
    }

    @Test
    void getCommentBySerialIDAndCommentId() throws Exception {
        when(commentService.getCommentById(1L))
                .thenReturn(createComment("some text"));
        mockMvc
                .perform(get("/api/serial/1/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postContent", is("some text")));
    }

    @Test
    void getCommentByGameIDAndCommentId() throws Exception {
        when(commentService.getCommentById(1L))
                .thenReturn(createComment("some text here!!!"));
        mockMvc
                .perform(get("/api//game/1/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postContent", is("some text here!!!")));
    }

    @Test
    void createMovieCommentWithAnonymousUserAndReturnForbidden() throws Exception {
        CommentAddBindingModel commentAddBindingModel = new CommentAddBindingModel();
        commentAddBindingModel.setPostContent("Test comment!");
        mockMvc
                .perform(post("/api/1/comments")
                        .content(objectMapper.writeValueAsString(commentAddBindingModel)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "Vomer")
    void createCommentForMovie() throws Exception {
        CommentAddBindingModel commentAddBindingModel = new CommentAddBindingModel();
        commentAddBindingModel.setPostContent("Test comment!");
        User user = new User();
        user.setUsername("Vomer");

        when(commentService.createCommentMovie(commentAddBindingModel, 1L, user.getUsername()))
                .thenReturn(createComment("Test comment!"));

        mockMvc
                .perform(post("/api/movie/1/comments")
                        .content(objectMapper.writeValueAsString(commentAddBindingModel))
                        .contentType("application/json")
                        .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "Vomer")
    void createCommentForMovieWithNullPostContent() throws Exception {
        CommentAddBindingModel commentAddBindingModel = new CommentAddBindingModel();
        commentAddBindingModel.setPostContent("Test comment!");
        User user = new User();
        user.setUsername("Vomer");

        when(commentService.createCommentMovie(commentAddBindingModel, 1L, user.getUsername()))
                .thenReturn(null);

        mockMvc
                .perform(post("/api/movie/1/comments")
                        .content(objectMapper.writeValueAsString(commentAddBindingModel))
                        .contentType("application/json")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "Vomer")
    void createCommentForSerial() throws Exception {
        CommentAddBindingModel commentAddBindingModel = new CommentAddBindingModel();
        commentAddBindingModel.setPostContent("Test comment!");
        User user = new User();
        user.setUsername("Vomer");

        when(commentService.createCommentSerial(
                commentAddBindingModel, 1L, user.getUsername()))
                .thenReturn(createComment("Test comment!"));

        mockMvc
                .perform(post("/api/serial/1/comments")
                        .content(objectMapper.writeValueAsString(commentAddBindingModel))
                        .contentType("application/json")
                        .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "Vomer")
    void createCommentForSerialWithNullPostContent() throws Exception {
        CommentAddBindingModel commentAddBindingModel = new CommentAddBindingModel();
        commentAddBindingModel.setPostContent("Test comment!");
        User user = new User();
        user.setUsername("Vomer");

        when(commentService.createCommentSerial(commentAddBindingModel, 1L, user.getUsername()))
                .thenReturn(null);

        mockMvc
                .perform(post("/api/serial/1/comments")
                        .content(objectMapper.writeValueAsString(commentAddBindingModel))
                        .contentType("application/json")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "Vomer")
    void createCommentForGame() throws Exception {
        CommentAddBindingModel commentAddBindingModel = new CommentAddBindingModel();
        commentAddBindingModel.setPostContent("Test comment!");
        User user = new User();
        user.setUsername("Vomer");

        when(commentService.createCommentGame(commentAddBindingModel, 1L, user.getUsername()))
                .thenReturn(createComment("Test comment!"));

        mockMvc
                .perform(post("/api/game/1/comments")
                        .content(objectMapper.writeValueAsString(commentAddBindingModel))
                        .contentType("application/json")
                        .with(csrf()))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "Vomer")
    void createCommentForGameWithNullPostContent() throws Exception {
        CommentAddBindingModel commentAddBindingModel = new CommentAddBindingModel();
        commentAddBindingModel.setPostContent("Test comment!");
        User user = new User();
        user.setUsername("Vomer");

        when(commentService.createCommentGame(commentAddBindingModel, 1L, user.getUsername()))
                .thenReturn(null);

        mockMvc
                .perform(post("/api/game/1/comments")
                        .content(objectMapper.writeValueAsString(commentAddBindingModel))
                        .contentType("application/json")
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    private CommentsView createComment(String text) {
        User author = new User();
        author.setUsername("Vomer");
        author.setFullName("Antoni Veznev");

        CommentsView comment = new CommentsView();
        comment.setId(1L);
        comment.setCreated("LocalDateTime.now()");
        comment.setPostContent(text);
        comment.setAuthorName(author.getUsername());

        return comment;
    }
}

