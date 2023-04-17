package com.example.star_wars_project.model.view;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommentViewTest {

    @Test
    void testGettersAndSetters() {
        CommentsView commentsView = new CommentsView();

        Long id = 1L;
        String authorName = "John";
        String postContent = "This is a post.";
        String created = "2022-01-01";

        commentsView.setId(id);
        commentsView.setAuthorName(authorName);
        commentsView.setPostContent(postContent);
        commentsView.setCreated(created);

        assertEquals(id, commentsView.getId());
        assertEquals(authorName, commentsView.getAuthorName());
        assertEquals(postContent, commentsView.getPostContent());
        assertEquals(created, commentsView.getCreated());
    }

}