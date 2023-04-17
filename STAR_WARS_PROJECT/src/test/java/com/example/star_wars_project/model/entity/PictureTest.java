package com.example.star_wars_project.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PictureTest {

    @Test
    public void testSetTitle() {
        Picture picture = new Picture();
        picture.setTitle("Sample Picture");
        Assertions.assertEquals("Sample Picture", picture.getTitle());
    }

    @Test
    public void testSetPictureUrl() {
        Picture picture = new Picture();
        picture.setPictureUrl("https://example.com/sample.jpg");
        Assertions.assertEquals("https://example.com/sample.jpg", picture.getPictureUrl());
    }

    @Test
    public void testSetPublicId() {
        Picture picture = new Picture();
        picture.setPublicId("123456");
        Assertions.assertEquals("123456", picture.getPublicId());
    }

    @Test
    public void testSetAuthor() {
        Picture picture = new Picture();
        User author = new User();
        author.setUsername("testuser");
        picture.setAuthor(author);
        Assertions.assertEquals(author, picture.getAuthor());
    }

    @Test
    public void testSetSeries() {
        Picture picture = new Picture();
        Series series = new Series();
        series.setTitle("Sample Series");
        picture.setSeries(series);
        Assertions.assertEquals(series, picture.getSeries());
    }

    @Test
    public void testSetMovie() {
        Picture picture = new Picture();
        Movie movie = new Movie();
        movie.setTitle("Sample Movie");
        picture.setMovie(movie);
        Assertions.assertEquals(movie, picture.getMovie());
    }

    @Test
    public void testSetGame() {
        Picture picture = new Picture();
        Game game = new Game();
        game.setTitle("Sample Game");
        picture.setGame(game);
        Assertions.assertEquals(game, picture.getGame());
    }

    @Test
    public void testSetNews() {
        Picture picture = new Picture();
        News news = new News();
        news.setTitle("Sample News");
        picture.setNews(news);
        Assertions.assertEquals(news, picture.getNews());
    }
}