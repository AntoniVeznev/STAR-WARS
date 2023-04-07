package com.example.star_wars_project.web;

import com.example.star_wars_project.model.binding.GameAddBindingModel;
import com.example.star_wars_project.model.binding.MovieAddBindingModel;
import com.example.star_wars_project.model.binding.NewsAddBindingModel;
import com.example.star_wars_project.model.binding.SeriesAddBindingModel;
import com.example.star_wars_project.service.*;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/add")
public class AddController {
    private final MovieService movieService;
    private final SeriesService seriesService;
    private final NewsService newsService;
    private final GameService gameService;

    public AddController(MovieService movieService, SeriesService seriesService, NewsService newsService, GameService gameService) {
        this.movieService = movieService;
        this.seriesService = seriesService;
        this.newsService = newsService;
        this.gameService = gameService;
    }

    @GetMapping("/news")
    public String addNews(Model model) {
        if (model.containsAttribute("pictureFileIsNotEmpty")) {
            model.addAttribute("pictureFileIsNotEmpty", true);
        }
        return "add-news";
    }

    @PostMapping("/news")
    public String addNewsConfirm(@Valid NewsAddBindingModel newsAddBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("newsAddBindingModel", newsAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.newsAddBindingModel", bindingResult);
            return "redirect:news";
        }

        if (newsAddBindingModel.getPicture().isEmpty()) {
            redirectAttributes.addFlashAttribute("newsAddBindingModel", newsAddBindingModel);
            redirectAttributes.addFlashAttribute("pictureFileIsNotEmpty", false);
            return "redirect:news";
        }

        String currentUserUsername = principal.getName();
        newsService.addNews(newsAddBindingModel, currentUserUsername);

        return "redirect:/";
    }

    @GetMapping("/movie")
    public String addMovie(Model model) {
        if (model.containsAttribute("pictureFileIsNotEmpty")) {
            model.addAttribute("pictureFileIsNotEmpty", true);
        }
        return "add-movie";
    }

    @PostMapping("/movie")
    public String addMovieConfirm(@Valid MovieAddBindingModel movieAddBindingModel,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  Principal principal) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("movieAddBindingModel", movieAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.movieAddBindingModel", bindingResult);
            return "redirect:movie";
        }

        if (movieAddBindingModel.getPicture().isEmpty()) {
            redirectAttributes.addFlashAttribute("movieAddBindingModel", movieAddBindingModel);
            redirectAttributes.addFlashAttribute("pictureFileIsNotEmpty", false);
            return "redirect:movie";
        }

        String currentUserUsername = principal.getName();
        movieService.addMovie(movieAddBindingModel, currentUserUsername);

        return "redirect:/";
    }

    @GetMapping("/serial")
    public String addSerial(Model model) {

        if (model.containsAttribute("pictureFileIsNotEmpty")) {
            model.addAttribute("pictureFileIsNotEmpty", true);
        }

        return "add-serial";
    }

    @PostMapping("/serial")
    public String addSerialConfirm(@Valid SeriesAddBindingModel seriesAddBindingModel,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   Principal principal) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("seriesAddBindingModel", seriesAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.seriesAddBindingModel", bindingResult);
            return "redirect:serial";
        }
        if (seriesAddBindingModel.getPicture().isEmpty()) {
            redirectAttributes.addFlashAttribute("seriesAddBindingModel", seriesAddBindingModel);
            redirectAttributes.addFlashAttribute("pictureFileIsNotEmpty", false);
            return "redirect:serial";
        }

        String currentUserUsername = principal.getName();
        seriesService.addSerial(seriesAddBindingModel, currentUserUsername);

        return "redirect:/";
    }

    @GetMapping("/game")
    public String addGame(Model model) {
        if (model.containsAttribute("pictureFileIsNotEmpty")) {
            model.addAttribute("pictureFileIsNotEmpty", true);
        }
        return "add-game";
    }

    @PostMapping("/game")
    public String addGameConfirm(@Valid GameAddBindingModel gameAddBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 Principal principal) throws IOException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("gameAddBindingModel", gameAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.gameAddBindingModel", bindingResult);
            return "redirect:game";
        }

        if (gameAddBindingModel.getPicture().isEmpty()) {
            redirectAttributes.addFlashAttribute("gameAddBindingModel", gameAddBindingModel);
            redirectAttributes.addFlashAttribute("pictureFileIsNotEmpty", false);
            return "redirect:game";
        }

        String currentUserUsername = principal.getName();
        gameService.addGame(gameAddBindingModel, currentUserUsername);

        return "redirect:/";
    }

    @ModelAttribute
    public MovieAddBindingModel movieAddBindingModel() {
        return new MovieAddBindingModel();
    }

    @ModelAttribute
    public SeriesAddBindingModel seriesAddBindingModel() {
        return new SeriesAddBindingModel();
    }

    @ModelAttribute
    public NewsAddBindingModel newsAddBindingModel() {
        return new NewsAddBindingModel();
    }

    @ModelAttribute
    public GameAddBindingModel gameAddBindingModel() {
        return new GameAddBindingModel();
    }
}