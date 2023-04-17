package com.example.star_wars_project.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.example.star_wars_project.exception.ItemNotFoundException;
import com.example.star_wars_project.model.entity.Picture;
import com.example.star_wars_project.model.entity.Series;
import com.example.star_wars_project.model.view.AllSerialsViewModel;
import com.example.star_wars_project.service.PictureService;
import com.example.star_wars_project.service.SeriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import org.springframework.web.servlet.ModelAndView;

class AllSerialsControllerTest {

    private AllSerialsController allSerialsController;
    private SeriesService seriesService;
    private PictureService pictureService;

    @BeforeEach
    void setUp() {
        seriesService = mock(SeriesService.class);
        pictureService = mock(PictureService.class);

        allSerialsController = new AllSerialsController(seriesService, pictureService);
    }

    @Test
    void testAllSerials() {

        List<AllSerialsViewModel> serials = Arrays.asList(new AllSerialsViewModel(), new AllSerialsViewModel());
        when(seriesService.findAllSerialsOrderedByReleaseDate()).thenReturn(serials);

        Model model = new ExtendedModelMap();

        String viewName = allSerialsController.allSerials(model);
        assertEquals("serials-catalogue", viewName);
        assertEquals(serials, model.getAttribute("serials"));
    }

    @Test
    void testDetails() {

        Long serialId = 1L;
        Series currentSerial = new Series();
        currentSerial.setId(serialId);
        Picture picture = new Picture();
        when(seriesService.findSerial(serialId)).thenReturn(currentSerial);
        when(pictureService.findPictureBySerialId(serialId)).thenReturn(picture);


        Model model = new ExtendedModelMap();


        String viewName = allSerialsController.details(serialId, model);
        assertEquals("serial-details", viewName);
        assertEquals(currentSerial, model.getAttribute("currentSerial"));
        assertEquals(picture, model.getAttribute("picture"));
    }


    @Test
    void testOnSerialNotFound() {

        ModelAndView mav = allSerialsController.onSerialNotFound(new ItemNotFoundException());
        assertEquals("other-errors/serial-not-found", mav.getViewName());
    }
}
