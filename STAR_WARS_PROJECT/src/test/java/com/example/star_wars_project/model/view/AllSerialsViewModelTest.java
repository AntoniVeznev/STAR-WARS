package com.example.star_wars_project.model.view;

import com.example.star_wars_project.model.entity.Picture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AllSerialsViewModelTest {

    @Test
    public void testGettersAndSetters() {
        AllSerialsViewModel serialsViewModel = new AllSerialsViewModel();

        Long id = 1L;
        String title = "Test Title";
        String description = "Test Description";
        Picture picture = new Picture();
        picture.setTitle("test_picture.jpg");

        serialsViewModel.setId(id);
        serialsViewModel.setTitle(title);
        serialsViewModel.setDescription(description);
        serialsViewModel.setPicture(picture);

        Assertions.assertEquals(id, serialsViewModel.getId());
        Assertions.assertEquals(title, serialsViewModel.getTitle());
        Assertions.assertEquals(description, serialsViewModel.getDescription());
        Assertions.assertEquals(picture, serialsViewModel.getPicture());
    }
}


