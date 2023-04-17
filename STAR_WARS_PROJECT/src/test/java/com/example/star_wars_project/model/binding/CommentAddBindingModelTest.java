package com.example.star_wars_project.model.binding;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CommentAddBindingModelTest {

    @Test
    void testGetCreated() {
        CommentAddBindingModel comment = new CommentAddBindingModel();
        LocalDateTime created = LocalDateTime.now();
        comment.setCreated(created);
        assertEquals(created, comment.getCreated());
    }

    @Test
    void testSetCreated() {
        CommentAddBindingModel comment = new CommentAddBindingModel();
        LocalDateTime created = LocalDateTime.now();
        comment.setCreated(created);
        assertEquals(created, comment.getCreated());
    }

    @Test
    void testGetPostContent() {
        CommentAddBindingModel comment = new CommentAddBindingModel();
        String postContent = "This is a test comment.";
        comment.setPostContent(postContent);
        assertEquals(postContent, comment.getPostContent());
    }

    @Test
    void testSetPostContent() {
        CommentAddBindingModel comment = new CommentAddBindingModel();
        String postContent = "This is a test comment.";
        comment.setPostContent(postContent);
        assertEquals(postContent, comment.getPostContent());
    }

}
