package com.example.star_wars_project.model.binding;

import com.example.star_wars_project.model.entity.enums.GenreNameEnum;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MovieAddBindingModelTest {

    @Test
    void testSetTitle() {
        MovieAddBindingModel movie = new MovieAddBindingModel();
        String title = "The Shawshank Redemption";
        movie.setTitle(title);
        assertEquals(title, movie.getTitle());
    }

    @Test
    void testSetReleaseDate() {
        MovieAddBindingModel movie = new MovieAddBindingModel();
        LocalDate releaseDate = LocalDate.now();
        movie.setReleaseDate(releaseDate);
        assertEquals(releaseDate, movie.getReleaseDate());
    }

    @Test
    void testSetGenre() {
        MovieAddBindingModel movie = new MovieAddBindingModel();
        GenreNameEnum genre = GenreNameEnum.ACTION;
        movie.setGenre(genre);
        assertEquals(genre, movie.getGenre());
    }

    @Test
    void testSetDescription() {
        MovieAddBindingModel movie = new MovieAddBindingModel();
        String description = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.";
        movie.setDescription(description);
        assertEquals(description, movie.getDescription());
    }

    @Test
    void testSetPictureTitle() {
        MovieAddBindingModel movie = new MovieAddBindingModel();
        String pictureTitle = "Shawshank Redemption Poster";
        movie.setPictureTitle(pictureTitle);
        assertEquals(pictureTitle, movie.getPictureTitle());
    }

    @Test
    void testSetPicture() {
        MovieAddBindingModel movie = new MovieAddBindingModel();
        MockMultipartFile picture = new MockMultipartFile("picture", new byte[0]);
        movie.setPicture(picture);
        assertEquals(picture, movie.getPicture());
    }

    @Test
    void testSetPublicId() {
        MovieAddBindingModel movie = new MovieAddBindingModel();
        String publicId = "abcd1234";
        movie.setPublicId(publicId);
        assertEquals(publicId, movie.getPublicId());
    }
}