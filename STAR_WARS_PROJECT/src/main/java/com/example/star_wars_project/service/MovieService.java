package com.example.star_wars_project.service;

import com.example.star_wars_project.model.binding.MovieAddBindingModel;
import com.example.star_wars_project.model.entity.Movie;
import com.example.star_wars_project.model.view.AllMoviesViewModel;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    List<AllMoviesViewModel> findAllMoviesOrderedByReleaseDate();

    List<AllMoviesViewModel> latestStarWarsMovies();

    Movie findMovie(Long id);

    void addMovie(MovieAddBindingModel movieAddBindingModel, String currentUserUsername) throws IOException;

    List<AllMoviesViewModel> findAllMoviesWithValueNullOrFalse();

    void approveMovieWithId(Long id);

    void deleteMovieWithId(Long id);

    void initMovies();
}