package com.example.star_wars_project.model.view;

import com.example.star_wars_project.model.entity.Picture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AllMoviesViewModelTest {

    @Test
    public void testGetAndSetId() {
        AllMoviesViewModel allMoviesViewModel = new AllMoviesViewModel();
        allMoviesViewModel.setId(123L);
        Assertions.assertEquals(123L, allMoviesViewModel.getId());
    }

    @Test
    public void testGetAndSetTitle() {
        AllMoviesViewModel allMoviesViewModel = new AllMoviesViewModel();
        allMoviesViewModel.setTitle("Movie Title");
        Assertions.assertEquals("Movie Title", allMoviesViewModel.getTitle());
    }

    @Test
    public void testGetAndSetDescription() {
        AllMoviesViewModel allMoviesViewModel = new AllMoviesViewModel();
        allMoviesViewModel.setDescription("Movie description");
        Assertions.assertEquals("Movie description", allMoviesViewModel.getDescription());
    }

    @Test
    public void testGetAndSetPicture() {
        AllMoviesViewModel allMoviesViewModel = new AllMoviesViewModel();
        Picture picture = new Picture();
        allMoviesViewModel.setPicture(picture);
        Assertions.assertEquals(picture, allMoviesViewModel.getPicture());
    }
}