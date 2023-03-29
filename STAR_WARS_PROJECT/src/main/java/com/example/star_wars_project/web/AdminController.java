package com.example.star_wars_project.web;

import com.example.star_wars_project.model.view.AllGamesViewModel;
import com.example.star_wars_project.model.view.AllMoviesViewModel;
import com.example.star_wars_project.model.view.AllNewsViewModel;
import com.example.star_wars_project.model.view.AllSerialsViewModel;
import com.example.star_wars_project.service.GameService;
import com.example.star_wars_project.service.MovieService;
import com.example.star_wars_project.service.NewsService;
import com.example.star_wars_project.service.SeriesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final MovieService movieService;
    private final SeriesService seriesService;
    private final GameService gameService;
    private final NewsService newsService;

    public AdminController(MovieService movieService, SeriesService seriesService, GameService gameService, NewsService newsService) {
        this.movieService = movieService;
        this.seriesService = seriesService;
        this.gameService = gameService;
        this.newsService = newsService;
    }

    @GetMapping()
    public String admin(Model model) {

        List<AllMoviesViewModel> allNotApprovedMovies = movieService.findAllMoviesWithValueNullOrFalse();
        List<AllSerialsViewModel> allNotApprovedSerials = seriesService.findAllSeriesWithValueNullOrFalse();
        List<AllGamesViewModel> allNotApprovedGames = gameService.findAllGamesWithValueNullOrFalse();
        List<AllNewsViewModel> allNotApprovedNews = newsService.findAllNewsWithValueNullOrFalse();

        model.addAttribute("allNotApprovedMovies", allNotApprovedMovies);
        model.addAttribute("allNotApprovedSerials", allNotApprovedSerials);
        model.addAttribute("allNotApprovedGames", allNotApprovedGames);
        model.addAttribute("allNotApprovedNews", allNotApprovedNews);

        return "admin";
    }

    @GetMapping("/movie/approve/{id}")
    public String movieApprove(@PathVariable Long id) {
        movieService.approveMovieWithId(id);
        return "redirect:/admin";
    }

    @GetMapping("/serial/approve/{id}")
    public String serialApprove(@PathVariable Long id) {
        seriesService.approveSerialWithId(id);
        return "redirect:/admin";
    }

    @GetMapping("/game/approve/{id}")
    public String gameApprove(@PathVariable Long id) {
        gameService.approveGameWithId(id);
        return "redirect:/admin";
    }

    @GetMapping("/news/approve/{id}")
    public String newsApprove(@PathVariable Long id) {
        newsService.approveNewsWithId(id);
        return "redirect:/admin";
    }


    @GetMapping("/movie/delete/{id}")
    public String movieDelete(@PathVariable Long id) {
        movieService.deleteMovieWithId(id);
        return "redirect:/admin";
    }

    @GetMapping("/serial/delete/{id}")
    public String serialDelete(@PathVariable Long id) {
        seriesService.deleteSerialWithId(id);
        return "redirect:/admin";
    }

    @GetMapping("/game/delete/{id}")
    public String gameDelete(@PathVariable Long id) {
        gameService.deleteGameWithId(id);
        return "redirect:/admin";
    }

    @GetMapping("/news/delete/{id}")
    public String newsDelete(@PathVariable Long id) {
        newsService.deleteNewsWithId(id);
        return "redirect:/admin";
    }

}
