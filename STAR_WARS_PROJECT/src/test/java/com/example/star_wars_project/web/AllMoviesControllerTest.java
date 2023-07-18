package com.example.star_wars_project.web;

import com.example.star_wars_project.exception.ItemNotFoundException;
import com.example.star_wars_project.model.entity.Movie;
import com.example.star_wars_project.model.entity.Picture;
import com.example.star_wars_project.model.view.AllMoviesViewModel;
import com.example.star_wars_project.service.MovieService;
import com.example.star_wars_project.service.PictureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AllMoviesControllerTest {

    @Mock
    private MovieService movieService;
    @Mock
    private PictureService pictureService;
    @Mock
    private Model model;
    private MockMvc mockMvc;
    private AllMoviesController allMoviesController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        allMoviesController = new AllMoviesController(movieService, pictureService);
        mockMvc = MockMvcBuilders.standaloneSetup(allMoviesController)
                .setControllerAdvice(new ItemNotFoundException())
                .build();
    }

    @Test
    void testAllMovies() {

        List<AllMoviesViewModel> expectedMovies = new ArrayList<>(); // create a list of expected movies

        AllMoviesViewModel movie1 = new AllMoviesViewModel();
        movie1.setTitle("title1");
        movie1.setDescription("descr1");
        movie1.setId(1L);

        AllMoviesViewModel movie2 = new AllMoviesViewModel();
        movie2.setTitle("title2");
        movie2.setDescription("descr2");
        movie2.setId(2L);
        expectedMovies.add(movie1);
        expectedMovies.add(movie2);

        when(movieService.findAllMoviesOrderedByReleaseDate()).thenReturn(expectedMovies);


        String viewName = allMoviesController.allMovies(model);


        assertThat(viewName).isEqualTo("movies-catalogue");
        verify(movieService).findAllMoviesOrderedByReleaseDate();
        verify(model).addAttribute("movies", expectedMovies);
    }

    @Test
    void testDetails() {

        Long movieId = 1L;
        Movie movie1 = new Movie();
        movie1.setTitle("title1");
        movie1.setDescription("descr1");
        movie1.setId(1L);
        Picture picture = new Picture();
        picture.setTitle("title");

        when(movieService.findMovie(movieId)).thenReturn(movie1);
        when(pictureService.findPictureByMovieId(movieId)).thenReturn(picture);

        String viewName = allMoviesController.details(movieId, model);

        assertThat(viewName).isEqualTo("movie-details");
        verify(movieService).findMovie(movieId);
        verify(pictureService).findPictureByMovieId(movieId);
        verify(model).addAttribute("currentMovie", movie1);
        verify(model).addAttribute("picture", picture);
    }
    @Test
    void testOnSerialNotFound() {

        ModelAndView mav = allMoviesController.onMovieNotFound(new ItemNotFoundException());
        assertEquals("other-errors/movie-not-found", mav.getViewName());
    }
    @Test
    void testMovieDetailsWhenMovieNotFound() throws Exception {
        Long id = 1L;
        Mockito.when(movieService.findMovie(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/movies/details/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.view().name("other-errors/movie-not-found"));
    }
}
