package com.example.star_wars_project.web;

import com.example.star_wars_project.model.entity.Game;
import com.example.star_wars_project.model.entity.Picture;
import com.example.star_wars_project.model.view.AllGamesViewModel;
import com.example.star_wars_project.service.GameService;
import com.example.star_wars_project.service.PictureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/games")
public class AllGamesController {
    private final GameService gameService;
    private final PictureService pictureService;
    public AllGamesController(GameService gameService, PictureService pictureService) {

        this.gameService = gameService;
        this.pictureService = pictureService;
    }
    @GetMapping("/catalogue")
    public String allGames(Model model) {
        List<AllGamesViewModel> games = gameService.findAllGamesOrderedByReleaseDate();
        model.addAttribute("games", games);
        return "games-catalogue";
    }



    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        Game currentGame = gameService.findGame(id);

        Picture picture = pictureService.findPictureByGameId(id);
        model.addAttribute("currentGame", currentGame);
        model.addAttribute("picture", picture);
        if (currentGame == null) {
            return "index";
        }
        return "game-details";
    }
}
