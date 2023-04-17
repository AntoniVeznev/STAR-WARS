package com.example.star_wars_project.model.binding;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import com.example.star_wars_project.model.entity.enums.GenreNameEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class SeriesAddBindingModelTest {

    private SeriesAddBindingModel series;

    @BeforeEach
    public void setUp() {
        series = new SeriesAddBindingModel();
    }

    @Test
    @DisplayName("Test setters and getters")
    public void testSettersAndGetters() {
        String title = "Breaking Bad";
        LocalDate releaseDate = LocalDate.now();
        GenreNameEnum genre = GenreNameEnum.CRIME;
        String description = "A high school chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine in order to secure his family's future.";
        String pictureTitle = "Breaking Bad Poster";
        MultipartFile picture = new MockMultipartFile("file", new byte[0]);
        String publicId = "abc123";

        series.setTitle(title);
        series.setReleaseDate(releaseDate);
        series.setGenre(genre);
        series.setDescription(description);
        series.setPictureTitle(pictureTitle);
        series.setPicture(picture);
        series.setPublicId(publicId);

        assertEquals(title, series.getTitle());
        assertEquals(releaseDate, series.getReleaseDate());
        assertEquals(genre, series.getGenre());
        assertEquals(description, series.getDescription());
        assertEquals(pictureTitle, series.getPictureTitle());
        assertEquals(picture, series.getPicture());
        assertEquals(publicId, series.getPublicId());
    }

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        assertNotNull(series);
    }

    @Test
    @DisplayName("Test empty constructor")
    public void testEmptyConstructor() {
        SeriesAddBindingModel emptySeries = new SeriesAddBindingModel();
        assertNotNull(emptySeries);
    }
}
