package com.example.star_wars_project.model.view;

import com.example.star_wars_project.model.entity.Picture;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AllGamesViewModelTest {

    @Test
    void testGetId() {
        AllGamesViewModel viewModel = new AllGamesViewModel();
        Long expectedId = 1234L;
        viewModel.setId(expectedId);

        Long actualId = viewModel.getId();

        assertEquals(expectedId, actualId);
    }

    @Test
    void testGetTitle() {
        AllGamesViewModel viewModel = new AllGamesViewModel();
        String expectedTitle = "My Game Title";
        viewModel.setTitle(expectedTitle);

        String actualTitle = viewModel.getTitle();

        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    void testGetDescription() {
        AllGamesViewModel viewModel = new AllGamesViewModel();
        String expectedDescription = "My game description";
        viewModel.setDescription(expectedDescription);

        String actualDescription = viewModel.getDescription();

        assertEquals(expectedDescription, actualDescription);
    }

    @Test
    void testGetPicture() {
        AllGamesViewModel viewModel = new AllGamesViewModel();
        Picture expectedPicture = new Picture();
        expectedPicture.setTitle("image.jpg");
        viewModel.setPicture(expectedPicture);

        Picture actualPicture = viewModel.getPicture();

        assertEquals(expectedPicture, actualPicture);
    }

    @Test
    void testSetId() {
        AllGamesViewModel viewModel = new AllGamesViewModel();
        Long expectedId = 1234L;

        viewModel.setId(expectedId);

        assertEquals(expectedId, viewModel.getId());
    }

    @Test
    void testSetTitle() {
        AllGamesViewModel viewModel = new AllGamesViewModel();
        String expectedTitle = "My Game Title";

        viewModel.setTitle(expectedTitle);

        assertEquals(expectedTitle, viewModel.getTitle());
    }

    @Test
    void testSetDescription() {
        AllGamesViewModel viewModel = new AllGamesViewModel();
        String expectedDescription = "My game description";

        viewModel.setDescription(expectedDescription);

        assertEquals(expectedDescription, viewModel.getDescription());
    }

    @Test
    void testSetPicture() {
        AllGamesViewModel viewModel = new AllGamesViewModel();
        Picture expectedPicture = new Picture();
        expectedPicture.setTitle("image.jpg");
        viewModel.setPicture(expectedPicture);

        assertEquals(expectedPicture, viewModel.getPicture());
    }
}
