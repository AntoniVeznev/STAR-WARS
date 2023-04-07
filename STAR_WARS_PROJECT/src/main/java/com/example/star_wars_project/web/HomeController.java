package com.example.star_wars_project.web;

import com.example.star_wars_project.model.view.AllMoviesViewModel;
import com.example.star_wars_project.model.view.AllNewsViewModel;
import com.example.star_wars_project.model.view.AllSerialsViewModel;
import com.example.star_wars_project.service.MovieService;
import com.example.star_wars_project.service.NewsService;
import com.example.star_wars_project.service.SeriesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private final MovieService movieService;
    private final SeriesService seriesService;
    private final NewsService newsService;

    public HomeController(MovieService movieService, SeriesService seriesService, NewsService newsService) {
        this.movieService = movieService;
        this.seriesService = seriesService;
        this.newsService = newsService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<AllMoviesViewModel> latestStarWarsMovies = movieService.latestStarWarsMovies();
        List<AllSerialsViewModel> latestStarWarsSeries = seriesService.latestStarWarsSerials();
        List<AllNewsViewModel> latestStarWarsNews = newsService.latestStarWarsNews();

        if (latestStarWarsNews.size() < 3) {
            model.addAttribute("notEnoughNews", true);
        } else {
            model.addAttribute("notEnoughNews", false);
        }

        model.addAttribute("latestStarWarsMovies", latestStarWarsMovies);
        model.addAttribute("latestStarWarsSeries", latestStarWarsSeries);
        model.addAttribute("latestStarWarsNews", latestStarWarsNews);

        return "index";
    }
}