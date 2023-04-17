package com.example.star_wars_project.model.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class SeriesTest {
    @Test
    public void testSetTitle() {
        Series series = new Series();
        series.setTitle("Test title");
        Assertions.assertEquals("Test title", series.getTitle());
    }

    @Test
    public void testSetDescription() {
        Series series = new Series();
        series.setDescription("Test description");
        Assertions.assertEquals("Test description", series.getDescription());
    }

    @Test
    public void testSetReleaseDate() {
        Series series = new Series();
        LocalDate releaseDate = LocalDate.now();
        series.setReleaseDate(releaseDate);
        Assertions.assertEquals(releaseDate, series.getReleaseDate());
    }

    @Test
    public void testSetAuthor() {
        Series series = new Series();
        User author = new User();
        series.setAuthor(author);
        Assertions.assertEquals(author, series.getAuthor());
    }

    @Test
    public void testSetGenre() {
        Series series = new Series();
        Genre genre = new Genre();
        series.setGenre(genre);
        Assertions.assertEquals(genre, series.getGenre());
    }

    @Test
    public void testSetApproved() {
        Series series = new Series();
        series.setApproved(true);
        Assertions.assertTrue(series.getApproved());
    }
}
