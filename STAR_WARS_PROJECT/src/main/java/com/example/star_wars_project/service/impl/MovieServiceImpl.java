package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.binding.MovieAddBindingModel;
import com.example.star_wars_project.model.entity.Movie;
import com.example.star_wars_project.model.entity.Picture;
import com.example.star_wars_project.model.view.AllMoviesViewModel;
import com.example.star_wars_project.repository.*;
import com.example.star_wars_project.utils.CloudinaryImage;
import com.example.star_wars_project.service.CloudinaryService;
import com.example.star_wars_project.service.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final ModelMapper modelMapper;
    private final MovieRepository movieRepository;
    private final SeriesRepository seriesRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final PictureRepository pictureRepository;
    private final CloudinaryService cloudinaryService;


    public MovieServiceImpl(ModelMapper modelMapper, MovieRepository movieRepository, SeriesRepository seriesRepository, UserRepository userRepository, GenreRepository genreRepository, PictureRepository pictureRepository, CloudinaryService cloudinaryService) {
        this.modelMapper = modelMapper;
        this.movieRepository = movieRepository;
        this.seriesRepository = seriesRepository;
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
        this.pictureRepository = pictureRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public List<AllMoviesViewModel> latestStarWarsMovies() {
        return movieRepository
                .findNewestFourMoviesByReleaseDate()
                .stream()
                .map(newestMovie -> {
                    AllMoviesViewModel newestMovies =
                            modelMapper.map(newestMovie, AllMoviesViewModel.class);
                    Picture pictureByMovieId =
                            pictureRepository.findPictureByMovie_Id(newestMovie.getId());
                    newestMovies.setPicture(pictureByMovieId);
                    return newestMovies;
                }).collect(Collectors.toList());

    }

    @Override
    public Movie findMovie(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    @Override
    public void addMovie(MovieAddBindingModel movieAddBindingModel, String currentUserUsername) throws IOException {
        Movie movie = modelMapper.map(movieAddBindingModel, Movie.class);
        movie.setAuthor(userRepository.findUserByUsername(currentUserUsername).orElse(null));
        movie.setGenre(genreRepository.findByName(movieAddBindingModel.getGenre()));
        movieRepository.save(movie);

        MultipartFile pictureMultipartFile = movieAddBindingModel.getPicture();
        String pictureMultipartFileTitle = movieAddBindingModel.getPictureTitle();
        final CloudinaryImage uploaded = cloudinaryService.upload(pictureMultipartFile);
        Picture picture = new Picture();

        picture.setPictureUrl(uploaded.getUrl());
        picture.setPublicId(uploaded.getPublicId());

        picture.setTitle(pictureMultipartFileTitle);
        picture.setAuthor(userRepository.findUserByUsername(currentUserUsername).orElse(null));

        picture.setMovie(movieRepository.findMovieByTitle(movieAddBindingModel.getTitle()));
        pictureRepository.save(picture);

    }

    @Override
    public List<AllMoviesViewModel> findAllMoviesWithValueNullOrFalse() {
        return movieRepository
                .findMoviesThatAreNotApproved()
                .stream()
                .map(movie -> {
                    AllMoviesViewModel currentMovie = modelMapper.map(movie, AllMoviesViewModel.class);
                    Picture pictureByMovieId = pictureRepository.findPictureByMovie_Id(movie.getId());
                    currentMovie.setPicture(pictureByMovieId);
                    return currentMovie;
                }).collect(Collectors.toList());
    }


    @Override
    public List<AllMoviesViewModel> findAllMoviesOrderedByReleaseDate() {
        return movieRepository
                .findAllMoviesByReleaseDate()
                .stream()
                .map(movie -> {
                    AllMoviesViewModel currentMovie = modelMapper.map(movie, AllMoviesViewModel.class);
                    Picture pictureByMovieId = pictureRepository.findPictureByMovie_Id(movie.getId());
                    currentMovie.setPicture(pictureByMovieId);
                    return currentMovie;
                }).collect(Collectors.toList());
    }

    @Override
    public void approveMovieWithId(Long id) {
        Movie movie = movieRepository.findMovieById(id);
        movie.setApproved(true);
        movieRepository.save(movie);
    }

    @Override
    public void deleteMovieWithId(Long id) {
        List<Picture> allByMovieId = pictureRepository.findAllByMovie_Id(id);
        pictureRepository.deleteAll(allByMovieId);
        movieRepository.deleteById(id);
    }
}
