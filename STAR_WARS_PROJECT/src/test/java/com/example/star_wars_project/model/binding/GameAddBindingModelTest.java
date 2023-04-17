package com.example.star_wars_project.model.binding;

import com.example.star_wars_project.model.entity.enums.PlatformNameEnum;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class GameAddBindingModelTest {

    @Test
    void setTitle() {
        String title = "This is a game title";
        GameAddBindingModel gameAddBindingModel = new GameAddBindingModel();
        gameAddBindingModel.setTitle(title);
        assertEquals(title, gameAddBindingModel.getTitle());
    }

    @Test
    void setReleaseDate() {
        LocalDate releaseDate = LocalDate.now();
        GameAddBindingModel gameAddBindingModel = new GameAddBindingModel();
        gameAddBindingModel.setReleaseDate(releaseDate);
        assertEquals(releaseDate, gameAddBindingModel.getReleaseDate());
    }

    @Test
    void setDescription() {
        String description = "This is a game description";
        GameAddBindingModel gameAddBindingModel = new GameAddBindingModel();
        gameAddBindingModel.setDescription(description);
        assertEquals(description, gameAddBindingModel.getDescription());
    }

    @Test
    void setVideoUrl() {
        String videoUrl = "https://www.youtube.com/watch?v=abc123";
        GameAddBindingModel gameAddBindingModel = new GameAddBindingModel();
        gameAddBindingModel.setVideoUrl(videoUrl);
        assertEquals(videoUrl, gameAddBindingModel.getVideoUrl());
    }

    @Test
    void setPlatform() {
        PlatformNameEnum platform = PlatformNameEnum.PC;
        GameAddBindingModel gameAddBindingModel = new GameAddBindingModel();
        gameAddBindingModel.setPlatform(platform);
        assertEquals(platform, gameAddBindingModel.getPlatform());
    }

    @Test
    void setPictureTitle() {
        String pictureTitle = "This is a picture title";
        GameAddBindingModel gameAddBindingModel = new GameAddBindingModel();
        gameAddBindingModel.setPictureTitle(pictureTitle);
        assertEquals(pictureTitle, gameAddBindingModel.getPictureTitle());
    }

    @Test
    void setPicture() {
        MultipartFile picture = mock(MultipartFile.class);
        GameAddBindingModel gameAddBindingModel = new GameAddBindingModel();
        gameAddBindingModel.setPicture(picture);
        assertEquals(picture, gameAddBindingModel.getPicture());
    }

    @Test
    void setPublicId() {
        String publicId = "public-id-123";
        GameAddBindingModel gameAddBindingModel = new GameAddBindingModel();
        gameAddBindingModel.setPublicId(publicId);
        assertEquals(publicId, gameAddBindingModel.getPublicId());
    }
}