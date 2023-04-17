package com.example.star_wars_project.model.view;

import com.example.star_wars_project.model.entity.Picture;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllNewsViewModelTest {

    @Test
    public void testGettersAndSetters() {
        AllNewsViewModel model = new AllNewsViewModel();
        Long id = 1L;
        String title = "Test News";
        String description = "This is a test news.";
        Picture picture = new Picture();
        picture.setTitle("test.jpg");
        LocalDateTime postDate = LocalDateTime.now();
        String authorName = "John Doe";

        model.setId(id);
        model.setTitle(title);
        model.setDescription(description);
        model.setPicture(picture);
        model.setPostDate(postDate);
        model.setAuthorName(authorName);

        assertEquals(id, model.getId());
        assertEquals(title, model.getTitle());
        assertEquals(description, model.getDescription());
        assertEquals(picture, model.getPicture());
        assertEquals(postDate, model.getPostDate());
        assertEquals(authorName, model.getAuthorName());
    }
}