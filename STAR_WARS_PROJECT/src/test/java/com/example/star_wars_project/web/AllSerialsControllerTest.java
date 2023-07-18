package com.example.star_wars_project.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.example.star_wars_project.exception.ItemNotFoundException;
import com.example.star_wars_project.model.entity.Picture;
import com.example.star_wars_project.model.entity.Series;
import com.example.star_wars_project.model.view.AllSerialsViewModel;
import com.example.star_wars_project.service.PictureService;
import com.example.star_wars_project.service.SeriesService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import org.springframework.web.servlet.ModelAndView;

class AllSerialsControllerTest {

    private AllSerialsController allSerialsController;
    private SeriesService seriesService;
    private PictureService pictureService;
    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        seriesService = mock(SeriesService.class);
        pictureService = mock(PictureService.class);
        allSerialsController = new AllSerialsController(seriesService, pictureService);
        mockMvc = MockMvcBuilders.standaloneSetup(allSerialsController)
                .setControllerAdvice(new ItemNotFoundException())
                .build();
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
    void testSerialDetailsWhenSerialNotFound() throws Exception {
        Long id = 1L;
        Mockito.when(seriesService.findSerial(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/series/details/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.view().name("other-errors/serial-not-found"));
    }


    @Test
    void testOnSerialNotFound() {

        ModelAndView mav = allSerialsController.onSerialNotFound(new ItemNotFoundException());
        assertEquals("other-errors/serial-not-found", mav.getViewName());
    }
}
