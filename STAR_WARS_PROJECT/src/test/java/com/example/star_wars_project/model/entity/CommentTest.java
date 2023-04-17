package com.example.star_wars_project.model.entity;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class CommentTest {

    @Test
    public void testGettersAndSetters() {
        Comment comment = new Comment();

        Boolean approved = true;
        LocalDateTime created = LocalDateTime.now();
        String postContent = "This is a test post content";
        Movie movie = new Movie();
        Series series = new Series();
        News news = new News();
        Game game = new Game();
        User author = new User();

        comment.setApproved(approved);
        comment.setCreated(created);
        comment.setPostContent(postContent);
        comment.setMovie(movie);
        comment.setSeries(series);
        comment.setNews(news);
        comment.setGame(game);
        comment.setAuthor(author);

        Assertions.assertEquals(approved, comment.getApproved());
        Assertions.assertEquals(created, comment.getCreated());
        Assertions.assertEquals(postContent, comment.getPostContent());
        Assertions.assertEquals(movie, comment.getMovie());
        Assertions.assertEquals(series, comment.getSeries());
        Assertions.assertEquals(news, comment.getNews());
        Assertions.assertEquals(game, comment.getGame());
        Assertions.assertEquals(author, comment.getAuthor());
    }
}
