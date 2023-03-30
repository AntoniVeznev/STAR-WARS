package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.binding.MovieAddBindingModel;
import com.example.star_wars_project.model.entity.Movie;
import com.example.star_wars_project.model.entity.Picture;
import com.example.star_wars_project.model.entity.Role;
import com.example.star_wars_project.model.entity.User;
import com.example.star_wars_project.model.entity.enums.GenreNameEnum;
import com.example.star_wars_project.model.view.AllMoviesViewModel;
import com.example.star_wars_project.repository.*;
import com.example.star_wars_project.utils.CloudinaryImage;
import com.example.star_wars_project.service.CloudinaryService;
import com.example.star_wars_project.service.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Override
    public void initMovies() {
        if (movieRepository.count() > 0) {
            return;
        }

        Movie movie1 = new Movie();
        movie1.setApproved(null);
        movie1.setDescription("Experience the heroic action and unforgettable adventures of Star Wars: Episode I - The Phantom Menace. See the first fateful steps in the journey of Anakin Skywalker. Stranded on the desert planet Tatooine after rescuing young Queen Amidala from the impending invasion of Naboo, Jedi apprentice Obi-Wan Kenobi and his Jedi Master Qui-Gon Jinn discover nine-year-old Anakin, a young slave unusually strong in the Force. Anakin wins a thrilling Podrace and with it his freedom as he leaves his home to be trained as a Jedi. The heroes return to Naboo where Anakin and the Queen face massive invasion forces while the two Jedi contend with a deadly foe named Darth Maul. Only then do they realize the invasion is merely the first step in a sinister scheme by the re-emergent forces of darkness known as the Sith.");
        movie1.setReleaseDate(LocalDate.of(1999, 5, 19));
        movie1.setTitle("Star Wars: The Phantom Menace (Episode I)");
        movie1.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        movie1.setGenre(genreRepository.findByName(GenreNameEnum.ACTION));


        Picture picture1 = new Picture();
        picture1.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680189968/Episode_1_u3qnvx.webp");
        picture1.setPublicId("Episode_1_u3qnvx");
        picture1.setTitle("Star Wars: The Phantom Menace (Episode I)");
        picture1.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture1.setMovie(movie1);

        movieRepository.save(movie1);
        pictureRepository.save(picture1);

        Movie movie2 = new Movie();
        movie2.setApproved(null);
        movie2.setDescription("Watch the seeds of Anakin Skywalker's transformation take root in Star Wars: Episode II - Attack of the Clones. Ten years after the invasion of Naboo, the galaxy is on the brink of civil war. Under the leadership of a renegade Jedi named Count Dooku, thousands of solar systems threaten to break away from the Galactic Republic. When an assassination attempt is made on Senator Padmé Amidala, the former Queen of Naboo, twenty-year-old Jedi apprentice Anakin Skywalker is assigned to protect her. In the course of his mission, Anakin discovers his love for Padmé as well as his own darker side. Soon, Anakin, Padmé, and Obi-Wan Kenobi are drawn into the heart of the Separatist movement and the beginning of the Clone Wars.");
        movie2.setReleaseDate(LocalDate.of(2002, 5, 17));
        movie2.setTitle("Star Wars: Attack of the Clones (Episode II)");
        movie2.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        movie2.setGenre(genreRepository.findByName(GenreNameEnum.ACTION));


        Picture picture2 = new Picture();
        picture2.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680189981/Episode_2_r9izui.webp");
        picture2.setPublicId("Episode_2_r9izui");
        picture2.setTitle("Star Wars: Attack of the Clones (Episode II)");
        picture2.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture2.setMovie(movie2);

        movieRepository.save(movie2);
        pictureRepository.save(picture2);

        Movie movie3 = new Movie();
        movie3.setApproved(null);
        movie3.setDescription("Discover the true power of the dark side in Star Wars: Episode III - Revenge of the Sith. Years after the onset of the Clone Wars, the noble Jedi Knights lead a massive clone army into a galaxy-wide battle against the Separatists. When the sinister Sith unveil a thousand-year-old plot to rule the galaxy, the Republic crumbles and from its ashes rises the evil Galactic Empire. Jedi hero Anakin Skywalker is seduced by the dark side of the Force to become the Emperor’s new apprentice – Darth Vader. The Jedi are decimated, as Obi-Wan Kenobi and Jedi Master Yoda are forced into hiding.");
        movie3.setReleaseDate(LocalDate.of(2005, 5, 19));
        movie3.setTitle("Star Wars: Revenge of the Sith (Episode III)");
        movie3.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        movie3.setGenre(genreRepository.findByName(GenreNameEnum.ACTION));


        Picture picture3 = new Picture();
        picture3.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680189985/Episode_3_tfoizd.webp");
        picture3.setPublicId("Episode_3_tfoizd");
        picture3.setTitle("Star Wars: Revenge of the Sith (Episode III)");
        picture3.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture3.setMovie(movie3);

        movieRepository.save(movie3);
        pictureRepository.save(picture3);

        Movie movie4 = new Movie();
        movie4.setApproved(null);
        movie4.setDescription("Luke Skywalker begins a journey that will change the galaxy in Star Wars: Episode IV - A New Hope. Nineteen years after the formation of the Empire, Luke is thrust into the struggle of the Rebel Alliance when he meets Obi-Wan Kenobi, who has lived for years in seclusion on the desert planet of Tatooine. Obi-Wan begins Luke’s Jedi training as Luke joins him on a daring mission to rescue the beautiful Rebel leader Princess Leia from the clutches of Darth Vader and the evil Empire.");
        movie4.setReleaseDate(LocalDate.of(1982, 4, 26));
        movie4.setTitle("Star Wars: A New Hope (Episode IV)");
        movie4.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        movie4.setGenre(genreRepository.findByName(GenreNameEnum.ACTION));


        Picture picture4 = new Picture();
        picture4.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680192571/Episode_4_enz7s3.webp");
        picture4.setPublicId("Episode_4_enz7s3");
        picture4.setTitle("Star Wars: A New Hope (Episode IV)");
        picture4.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture4.setMovie(movie4);

        movieRepository.save(movie4);
        pictureRepository.save(picture4);
    }

}
