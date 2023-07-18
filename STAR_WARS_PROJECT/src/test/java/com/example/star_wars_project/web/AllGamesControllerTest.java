package com.example.star_wars_project.web;

import com.example.star_wars_project.exception.ItemNotFoundException;
import com.example.star_wars_project.model.entity.Game;
import com.example.star_wars_project.model.entity.Picture;
import com.example.star_wars_project.model.view.AllGamesViewModel;
import com.example.star_wars_project.service.GameService;
import com.example.star_wars_project.service.PictureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AllGamesControllerTest {

    @Mock
    private GameService gameService;

    @Mock
    private PictureService pictureService;

    @InjectMocks
    private AllGamesController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ItemNotFoundException())
                .build();
    }

    @Test
    void testAllGames() throws Exception {
        List<AllGamesViewModel> games = Arrays.asList(
                new AllGamesViewModel(),
                new AllGamesViewModel(),
                new AllGamesViewModel()
        );
        Mockito.when(gameService.findAllGamesOrderedByReleaseDate()).thenReturn(games);

        mockMvc.perform(MockMvcRequestBuilders.get("/games/catalogue"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("games-catalogue"))
                .andExpect(MockMvcResultMatchers.model().attribute("games", games));
    }

    @Test
    void testGameDetails() throws Exception {
        Long id = 1L;
        Game game = new Game();
        Picture picture = new Picture();
        Mockito.when(gameService.findGame(id)).thenReturn(game);
        Mockito.when(pictureService.findPictureByGameId(id)).thenReturn(picture);

        mockMvc.perform(MockMvcRequestBuilders.get("/games/details/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("game-details"))
                .andExpect(MockMvcResultMatchers.model().attribute("currentGame", game))
                .andExpect(MockMvcResultMatchers.model().attribute("picture", picture));
    }

    @Test
    void testGameDetailsWhenGameNotFound() throws Exception {
        Long id = 1L;
        Mockito.when(gameService.findGame(id)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/games/details/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.view().name("other-errors/game-not-found"));
    }
    @Test
    void testOnSerialNotFound() {

        ModelAndView mav = controller.onGameNotFound(new ItemNotFoundException());
        assertEquals("other-errors/game-not-found", mav.getViewName());
    }
}
