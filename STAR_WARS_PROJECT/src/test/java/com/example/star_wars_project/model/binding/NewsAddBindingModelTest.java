package com.example.star_wars_project.model.binding;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewsAddBindingModelTest {
    private static final String VALID_TITLE = "Test Title";
    private static final LocalDateTime VALID_POST_DATE = LocalDateTime.now();
    private static final String VALID_DESCRIPTION = "Test Description";
    private static final String VALID_PICTURE_TITLE = "Test Picture Title";
    private static final String VALID_PUBLIC_ID = "Test Public Id";
    private static MultipartFile multipartFile;

    private NewsAddBindingModel newsAddBindingModel;

    @BeforeEach
    public void setup() {
        newsAddBindingModel = new NewsAddBindingModel();
    }
    @Test
    public void testSetPicture() {
        newsAddBindingModel.setPicture(multipartFile);
        assertEquals(multipartFile, newsAddBindingModel.getPicture());
    }

    @Test
    public void testSetTitle() {
        newsAddBindingModel.setTitle(VALID_TITLE);
        assertEquals(VALID_TITLE, newsAddBindingModel.getTitle());
    }

    @Test
    public void testSetPostDate() {
        newsAddBindingModel.setPostDate(VALID_POST_DATE);
        assertEquals(VALID_POST_DATE, newsAddBindingModel.getPostDate());
    }

    @Test
    public void testSetDescription() {
        newsAddBindingModel.setDescription(VALID_DESCRIPTION);
        assertEquals(VALID_DESCRIPTION, newsAddBindingModel.getDescription());
    }

    @Test
    public void testSetPictureTitle() {
        newsAddBindingModel.setPictureTitle(VALID_PICTURE_TITLE);
        assertEquals(VALID_PICTURE_TITLE, newsAddBindingModel.getPictureTitle());
    }

    @Test
    public void testSetPublicId() {
        newsAddBindingModel.setPublicId(VALID_PUBLIC_ID);
        assertEquals(VALID_PUBLIC_ID, newsAddBindingModel.getPublicId());
    }
}