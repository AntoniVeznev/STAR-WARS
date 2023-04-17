package com.example.star_wars_project.web;

import com.example.star_wars_project.model.view.*;
import com.example.star_wars_project.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    private AdminController adminController;

    @Mock
    private MovieService movieService;
    @Mock
    private SeriesService seriesService;
    @Mock
    private GameService gameService;
    @Mock
    private NewsService newsService;
    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        adminController = new AdminController(movieService, seriesService, gameService, newsService, userService);
    }


    @Test
    void movieApprove() {
        Long id = 1L;
        String viewName = adminController.movieApprove(id);
        assertEquals("redirect:/admin", viewName);
        verify(movieService).approveMovieWithId(id);
        verifyNoMoreInteractions(movieService);
    }

    @Test
    void serialApprove() {
        Long id = 1L;
        String viewName = adminController.serialApprove(id);
        assertEquals("redirect:/admin", viewName);
        verify(seriesService).approveSerialWithId(id);
        verifyNoMoreInteractions(seriesService);
    }

    @Test
    void gameApprove() {
        Long id = 1L;
        String viewName = adminController.gameApprove(id);
        assertEquals("redirect:/admin", viewName);
        verify(gameService).approveGameWithId(id);
        verifyNoMoreInteractions(gameService);
    }

    @Test
    void newsApprove() {
        Long id = 1L;
        String viewName = adminController.newsApprove(id);
        assertEquals("redirect:/admin", viewName);
        verify(newsService).approveNewsWithId(id);
        verifyNoMoreInteractions(newsService);
    }

    @Test
    public void testMovieDelete() {
        Long movieId = 1L;

        String expectedRedirectUrl = "redirect:/admin";
        String actualRedirectUrl = adminController.movieDelete(movieId);

        verify(movieService, times(1)).deleteMovieWithId(movieId);
        assertEquals(expectedRedirectUrl, actualRedirectUrl);
    }

    @Test
    void testSerialDelete() {
        String expectedRedirect = "redirect:/admin";
        Long id = 1L;

        String actualRedirect = adminController.serialDelete(id);

        verify(seriesService).deleteSerialWithId(id);
        assert (expectedRedirect.equals(actualRedirect));
    }

    @Test
    void testGameDelete() {
        String expectedRedirect = "redirect:/admin";
        Long id = 1L;

        String actualRedirect = adminController.gameDelete(id);

        verify(gameService).deleteGameWithId(id);
        assert (expectedRedirect.equals(actualRedirect));
    }

    @Test
    void testNewsDelete() {
        String expectedRedirect = "redirect:/admin";
        Long id = 1L;

        String actualRedirect = adminController.newsDelete(id);

        verify(newsService).deleteNewsWithId(id);
        assert (expectedRedirect.equals(actualRedirect));
    }

    @Test
    void testUserApprove() {
        String expectedRedirect = "redirect:/admin";
        Long id = 1L;

        String actualRedirect = adminController.userApprove(id);

        verify(userService).promoteUserWithId(id);
        assert (expectedRedirect.equals(actualRedirect));
    }

    @Test
    void testUserDelete() {
        String expectedRedirect = "redirect:/admin";
        Long id = 1L;

        String actualRedirect = adminController.userDelete(id);

        verify(userService).deleteUserWithId(id);
        assert (expectedRedirect.equals(actualRedirect));
    }

    @Test
    void admin_ShouldReturnAdminViewAndModelAttributes() {

        String loggedInAdminName = "admin";
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(loggedInAdminName);

        List<AllMoviesViewModel> allNotApprovedMovies = Arrays.asList(new AllMoviesViewModel());
        when(movieService.findAllMoviesWithValueNullOrFalse()).thenReturn(allNotApprovedMovies);

        List<AllSerialsViewModel> allNotApprovedSerials = Arrays.asList(new AllSerialsViewModel());
        when(seriesService.findAllSeriesWithValueNullOrFalse()).thenReturn(allNotApprovedSerials);

        List<AllGamesViewModel> allNotApprovedGames = Arrays.asList(new AllGamesViewModel());
        when(gameService.findAllGamesWithValueNullOrFalse()).thenReturn(allNotApprovedGames);

        List<AllNewsViewModel> allNotApprovedNews = Arrays.asList(new AllNewsViewModel());
        when(newsService.findAllNewsWithValueNullOrFalse()).thenReturn(allNotApprovedNews);

        List<AllUsersViewModel> allUsersWithRoleUSER = Arrays.asList(new AllUsersViewModel());
        when(userService.findAllUsersWithRoleUSER()).thenReturn(allUsersWithRoleUSER);


        String viewName = adminController.admin(model, principal);


        verify(movieService).findAllMoviesWithValueNullOrFalse();
        verify(seriesService).findAllSeriesWithValueNullOrFalse();
        verify(gameService).findAllGamesWithValueNullOrFalse();
        verify(newsService).findAllNewsWithValueNullOrFalse();
        verify(userService).findAllUsersWithRoleUSER();

        assertEquals("admin", viewName);
        verify(model).addAttribute("loggedInAdminName", loggedInAdminName);
        verify(model).addAttribute("allNotApprovedMovies", allNotApprovedMovies);
        verify(model).addAttribute("allNotApprovedSerials", allNotApprovedSerials);

        verify(model).addAttribute("allNotApprovedGames", allNotApprovedGames);
        verify(model).addAttribute("allNotApprovedNews", allNotApprovedNews);
        verify(model).addAttribute("allUsersWithRoleUSER", allUsersWithRoleUSER);
    }
}
