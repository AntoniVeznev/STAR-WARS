package com.example.star_wars_project.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import com.example.star_wars_project.model.view.AllMoviesViewModel;
import com.example.star_wars_project.model.view.AllNewsViewModel;
import com.example.star_wars_project.model.view.AllSerialsViewModel;
import com.example.star_wars_project.service.MovieService;
import com.example.star_wars_project.service.NewsService;
import com.example.star_wars_project.service.SeriesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

class HomeControllerTest {

    private HomeController homeController;
    private MovieService movieService;
    private SeriesService seriesService;
    private NewsService newsService;

    @BeforeEach
    void setUp() {
        movieService = mock(MovieService.class);
        seriesService = mock(SeriesService.class);
        newsService = mock(NewsService.class);

        homeController = new HomeController(movieService, seriesService, newsService);
    }

    @Test
    void testIndex() {

        List<AllMoviesViewModel> movies = Arrays.asList(new AllMoviesViewModel(), new AllMoviesViewModel());
        List<AllSerialsViewModel> serials = Arrays.asList(new AllSerialsViewModel(), new AllSerialsViewModel());
        List<AllNewsViewModel> news = Arrays.asList(new AllNewsViewModel(), new AllNewsViewModel(), new AllNewsViewModel());
        when(movieService.latestStarWarsMovies()).thenReturn(movies);
        when(seriesService.latestStarWarsSerials()).thenReturn(serials);
        when(newsService.latestStarWarsNews()).thenReturn(news);


        Model model = new ExtendedModelMap();


        String viewName = homeController.index(model);
        assertEquals("index", viewName);
        assertEquals(movies, model.getAttribute("latestStarWarsMovies"));
        assertEquals(serials, model.getAttribute("latestStarWarsSeries"));
        assertEquals(news, model.getAttribute("latestStarWarsNews"));
        assertFalse((Boolean) model.getAttribute("notEnoughNews"));
    }
    @Test
    void testIndexNewsError() {

        List<AllMoviesViewModel> movies = Arrays.asList(new AllMoviesViewModel(), new AllMoviesViewModel());
        List<AllSerialsViewModel> serials = Arrays.asList(new AllSerialsViewModel(), new AllSerialsViewModel());
        List<AllNewsViewModel> news = Arrays.asList(new AllNewsViewModel(), new AllNewsViewModel());
        when(movieService.latestStarWarsMovies()).thenReturn(movies);
        when(seriesService.latestStarWarsSerials()).thenReturn(serials);
        when(newsService.latestStarWarsNews()).thenReturn(news);


        Model model = new ExtendedModelMap();


        String viewName = homeController.index(model);
        assertEquals("index", viewName);
        assertEquals(movies, model.getAttribute("latestStarWarsMovies"));
        assertEquals(serials, model.getAttribute("latestStarWarsSeries"));
        assertEquals(news, model.getAttribute("latestStarWarsNews"));
        assertTrue((Boolean) model.getAttribute("notEnoughNews"));
    }
}
