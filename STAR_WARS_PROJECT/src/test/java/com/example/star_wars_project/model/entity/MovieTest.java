package com.example.star_wars_project.model.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MovieTest {
    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie();
    }

    @Test
    void testSetTitle() {
        String title = "Test Movie Title";
        movie.setTitle(title);
        assertEquals(title, movie.getTitle());
    }

    @Test
    void testSetDescription() {
        String description = "Test Movie Description";
        movie.setDescription(description);
        assertEquals(description, movie.getDescription());
    }

    @Test
    void testSetReleaseDate() {
        LocalDate releaseDate = LocalDate.now();
        movie.setReleaseDate(releaseDate);
        assertEquals(releaseDate, movie.getReleaseDate());
    }

    @Test
    void testSetAuthor() {
        User author = new User();
        movie.setAuthor(author);
        assertEquals(author, movie.getAuthor());
    }

    @Test
    void testSetGenre() {
        Genre genre = new Genre();
        movie.setGenre(genre);
        assertEquals(genre, movie.getGenre());
    }

    @Test
    void testSetApproved() {
        Boolean isApproved = true;
        movie.setApproved(isApproved);
        assertEquals(isApproved, movie.getApproved());
    }
}