package com.example.star_wars_project.web;

import com.example.star_wars_project.exception.ItemNotFoundException;
import com.example.star_wars_project.model.entity.Movie;
import com.example.star_wars_project.model.entity.Picture;
import com.example.star_wars_project.model.view.AllMoviesViewModel;
import com.example.star_wars_project.service.MovieService;
import com.example.star_wars_project.service.PictureService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class AllMoviesController {
    private final MovieService movieService;
    private final PictureService pictureService;

    public AllMoviesController(MovieService movieService, PictureService pictureService) {
        this.movieService = movieService;
        this.pictureService = pictureService;
    }

    @GetMapping("/catalogue")
    public String allMovies(Model model) {
        List<AllMoviesViewModel> movies = movieService.findAllMoviesOrderedByReleaseDate();
        model.addAttribute("movies", movies);
        return "movies-catalogue";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        Movie currentMovie = movieService.findMovie(id);
        Picture picture = pictureService.findPictureByMovieId(id);
        model.addAttribute("currentMovie", currentMovie);
        model.addAttribute("picture", picture);
        if (currentMovie == null) {
            throw new ItemNotFoundException();
        }
        return "movie-details";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException.class)
    public ModelAndView onMovieNotFound(ItemNotFoundException infe) {
        return new ModelAndView("other-errors/movie-not-found");
    }
}