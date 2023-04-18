package com.example.star_wars_project.web;

import com.example.star_wars_project.model.binding.MovieAddBindingModel;
import com.example.star_wars_project.model.binding.NewsAddBindingModel;
import com.example.star_wars_project.model.binding.SeriesAddBindingModel;
import com.example.star_wars_project.service.GameService;
import com.example.star_wars_project.service.MovieService;
import com.example.star_wars_project.service.NewsService;
import com.example.star_wars_project.service.SeriesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AddControllerTest {

    @Mock
    private NewsService newsService;
    @InjectMocks
    private AddController addController;
    @Mock
    private NewsAddBindingModel newsAddBindingModel;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private RedirectAttributes redirectAttributes;
    @Mock
    private Principal principal;
    @Mock
    private MovieService movieService;
    @Mock
    private SeriesService seriesService;

    @Mock
    private GameService gameService;

    private MockMvc mockMvc;

    @Test
    void testAddNewsReturnsCorrectViewName() {

        Model model = new ExtendedModelMap();

        String viewName = addController.addNews(model);

        assertEquals("add-news", viewName);
    }

    @Test
    void testAddNewsSetsPictureFileIsNotEmptyModelAttributeWhenPresent() {

        Model model = new ExtendedModelMap();
        model.addAttribute("pictureFileIsNotEmpty", true);

        addController.addNews(model);

        assertTrue((Boolean) model.getAttribute("pictureFileIsNotEmpty"));
    }

    @Test
    void testAddMovie() {
        Model model = new ExtendedModelMap();
        String viewName = addController.addMovie(model);
        assertEquals("add-movie", viewName);
        assertFalse(model.containsAttribute("pictureFileIsNotEmpty"));
    }

    @Test
    void testAddSerial() {
        Model model = new ExtendedModelMap();
        String viewName = addController.addSerial(model);
        assertEquals("add-serial", viewName);
        assertFalse(model.containsAttribute("pictureFileIsNotEmpty"));
    }

    @Test
    void testAddGame() {
        Model model = new ExtendedModelMap();
        String viewName = addController.addGame(model);
        assertEquals("add-game", viewName);
        assertFalse(model.containsAttribute("pictureFileIsNotEmpty"));
    }


    @Test
    void addNewsConfirm_withInvalidInput_shouldReturnRedirectToAddNewsPage() throws IOException {

        when(bindingResult.hasErrors()).thenReturn(true);

        String result = addController.addNewsConfirm(newsAddBindingModel, bindingResult, redirectAttributes, principal);

        verify(redirectAttributes, times(1)).addFlashAttribute(eq("newsAddBindingModel"), eq(newsAddBindingModel));
        verify(redirectAttributes, times(1)).addFlashAttribute(eq(BindingResult.MODEL_KEY_PREFIX + "newsAddBindingModel"), eq(bindingResult));
        assertEquals("redirect:news", result);
    }


    @Test
    public void testAddMovieConfirm() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(addController).build();

        MovieAddBindingModel movieAddBindingModel = new MovieAddBindingModel();
        movieAddBindingModel.setTitle("Test Movie");

        mockMvc.perform(post("/add/movie")
                        .flashAttr("movieAddBindingModel", movieAddBindingModel))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testAddSerialConfirm() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(addController).build();

        SeriesAddBindingModel seriesAddBindingModel = new SeriesAddBindingModel();
        seriesAddBindingModel.setTitle("Test Series");

        mockMvc.perform(post("/add/serial")
                        .flashAttr("seriesAddBindingModel", seriesAddBindingModel))
                .andExpect(status().is3xxRedirection());
    }
}