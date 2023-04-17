package com.example.star_wars_project.initialization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.example.star_wars_project.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DataBaseInitTest {
    @Mock
    private RoleService roleService;
    @Mock
    private GenreService genreService;
    @Mock
    private PlatformService platformService;
    @Mock
    private UserService userService;
    @Mock
    private MovieService movieService;
    @Mock
    private SeriesService seriesService;
    @Mock
    private NewsService newsService;
    @Mock
    private GameService gameService;
    private DataBaseInit dataBaseInit;

    @BeforeEach
    public void setUp() {
        dataBaseInit = new DataBaseInit(roleService, genreService, platformService, userService, movieService, seriesService, newsService, gameService);
    }

    @Test
    public void testRunCallsInitMethods() {
        dataBaseInit.run();
        verify(roleService, times(1)).initRoles();
        verify(genreService, times(1)).initGenres();
        verify(platformService, times(1)).initPlatforms();
        verify(userService, times(1)).initAdminAndUser();
        verify(movieService, times(1)).initMovies();
        verify(seriesService, times(1)).initSeries();
        verify(newsService, times(1)).initNews();
        verify(gameService, times(1)).initGames();
    }

    @Test
    public void testConstructorInitializesFields() {
        DataBaseInit dataBaseInit = new DataBaseInit(roleService, genreService, platformService, userService, movieService, seriesService, newsService, gameService);
        assertEquals(roleService, dataBaseInit.roleService);
        assertEquals(genreService, dataBaseInit.genreService);
        assertEquals(platformService, dataBaseInit.platformService);
        assertEquals(userService, dataBaseInit.userService);
        assertEquals(movieService, dataBaseInit.movieService);
        assertEquals(seriesService, dataBaseInit.seriesService);
        assertEquals(newsService, dataBaseInit.newsService);
        assertEquals(gameService, dataBaseInit.gameService);
    }
}
