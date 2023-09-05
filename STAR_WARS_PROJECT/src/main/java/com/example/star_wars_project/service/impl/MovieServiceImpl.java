package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.binding.MovieAddBindingModel;
import com.example.star_wars_project.model.entity.Movie;
import com.example.star_wars_project.model.entity.Picture;
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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {
    private final ModelMapper modelMapper;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final PictureRepository pictureRepository;
    private final CloudinaryService cloudinaryService;

    public MovieServiceImpl(ModelMapper modelMapper, MovieRepository movieRepository, UserRepository userRepository, GenreRepository genreRepository, PictureRepository pictureRepository, CloudinaryService cloudinaryService) {
        this.modelMapper = modelMapper;
        this.movieRepository = movieRepository;
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
                .map(this::mapsMovieToMovieView)
                .collect(Collectors.toList());
    }

    @Override
    public List<AllMoviesViewModel> findAllMoviesWithValueNullOrFalse() {
        return movieRepository
                .findMoviesThatAreNotApproved()
                .stream()
                .map(this::mapsMovieToMovieView)
                .collect(Collectors.toList());
    }

    @Override
    public List<AllMoviesViewModel> findAllMoviesOrderedByReleaseDate() {
        return movieRepository
                .findAllMoviesByReleaseDate()
                .stream()
                .map(this::mapsMovieToMovieView)
                .collect(Collectors.toList());
    }

    private AllMoviesViewModel mapsMovieToMovieView(Movie movie) {
        AllMoviesViewModel currentMovie = modelMapper.map(movie, AllMoviesViewModel.class);
        Picture pictureByMovieId = pictureRepository.findPictureByMovie_Id(movie.getId());
        currentMovie.setPicture(pictureByMovieId);
        return currentMovie;
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


        Movie movie5 = new Movie();
        movie5.setApproved(null);
        movie5.setDescription("After the destruction of the Death Star, Imperial forces continue to pursue the Rebels. After the Rebellion’s defeat on the ice planet Hoth, Luke journeys to the planet Dagobah to train with Jedi Master Yoda, who has lived in hiding since the fall of the Republic. In an attempt to convert Luke to the dark side, Darth Vader lures young Skywalker into a trap in the Cloud City of Bespin.  ");
        movie5.setReleaseDate(LocalDate.of(1984, 4, 23));
        movie5.setTitle("Star Wars: The Empire Strikes Back (Episode V)");
        movie5.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        movie5.setGenre(genreRepository.findByName(GenreNameEnum.ACTION));


        Picture picture5 = new Picture();
        picture5.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1691913272/w11bn4jweafct3q7dxry.jpg");
        picture5.setPublicId("w11bn4jweafct3q7dxry");
        picture5.setTitle("Star Wars: The Empire Strikes Back (Episode V)");
        picture5.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture5.setMovie(movie5);

        movieRepository.save(movie5);
        pictureRepository.save(picture5);


        Movie movie6 = new Movie();
        movie6.setApproved(null);
        movie6.setDescription("After a quick trip back to Tatooine, Luke Skywalker, Leia Organa, and Han Solo are reunited and join up with the amassing rebel fleet to take down the evil Empire once and for all. But the Empire is plotting too. Emperor Palpatine commands his troops aboard his newly consturcted Death Star stationed above the forest moon of Endor, where the rebels - and some unlikely furry friends - make their stand against the Empire. While Luke Skywalker confronts Darth Vader on the Death Star once more, Han leads a team to take out a shield protecting the battle station so that the rebel fleet can destory it once more and finally put an end to the war...  ");
        movie6.setReleaseDate(LocalDate.of(1986, 4, 21));
        movie6.setTitle("Star Wars: Return of the Jedi (Episode VI)");
        movie6.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        movie6.setGenre(genreRepository.findByName(GenreNameEnum.ACTION));


        Picture picture6 = new Picture();
        picture6.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1691913589/yax7nt8wtgalbbm7bal4.jpg");
        picture6.setPublicId("yax7nt8wtgalbbm7bal4");
        picture6.setTitle("Star Wars: Return of the Jedi (Episode VI)");
        picture6.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture6.setMovie(movie6);

        movieRepository.save(movie6);
        pictureRepository.save(picture6);


        Movie movie7 = new Movie();
        movie7.setApproved(null);
        movie7.setDescription("Thirty years since the destruction of the second Death Star, the sinister First Order, commanded by the mysterious Snoke and apprentice Kylo Ren, rise from the ashes of the Empire. The Resistance, led by General Leia Organa, attempts to thwart the First Order's threat, but they're desparate for help. Rey, a desert scavenger, and Finn, an ex-stormtrooper, find themselves joining forces wiiht Han Solo and Chewbacca in a desperate mission to return a BB-unit droid with a map to Luke Skywalker back to the Resistance.");
        movie7.setReleaseDate(LocalDate.of(2015, 12, 18));
        movie7.setTitle("Star Wars: The Force Awakens (Episode VII)");
        movie7.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        movie7.setGenre(genreRepository.findByName(GenreNameEnum.ACTION));


        Picture picture7 = new Picture();
        picture7.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1691913845/zy5nttq7qyu25n9n9txf.jpg");
        picture7.setPublicId("zy5nttq7qyu25n9n9txf");
        picture7.setTitle("Star Wars: The Force Awakens (Episode VII)");
        picture7.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture7.setMovie(movie7);

        movieRepository.save(movie7);
        pictureRepository.save(picture7);


        Movie movie8 = new Movie();
        movie8.setApproved(null);
        movie8.setDescription("From Lucasfilm comes the first of the Star Wars standalone films, “Rogue One: A Star Wars Story,” an all-new epic adventure. In a time of conflict, a group of unlikely heroes band together on a mission to steal the plans to the Death Star, the Empire’s ultimate weapon of destruction. This key event in the Star Wars timeline brings together ordinary people who choose to do extraordinary things, and in doing so, become part of something greater than themselves.  ");
        movie8.setReleaseDate(LocalDate.of(2016, 12, 16));
        movie8.setTitle("Rogue One: A Star Wars Story");
        movie8.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        movie8.setGenre(genreRepository.findByName(GenreNameEnum.CRIME));


        Picture picture8 = new Picture();
        picture8.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1691914662/rjp6ojtw1e1rf7nvqg4j.jpg");
        picture8.setPublicId("rjp6ojtw1e1rf7nvqg4j");
        picture8.setTitle("Rogue One: A Star Wars Story");
        picture8.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture8.setMovie(movie8);

        movieRepository.save(movie8);
        pictureRepository.save(picture8);


        Movie movie9 = new Movie();
        movie9.setApproved(null);
        movie9.setDescription("The Resistance is in desperate need of help when they find themselves impossibly pursued by the First Order. While Rey travels to a remote planet called Ahch-To to recruit Luke Skywalker to the Resistance, Finn and Rose, a mechanic, go on their own mission in the hopes of helping the Resistance finally escape the First Order. But everyone finds themselves on the salt-planet of Crait for a last stand.   ");
        movie9.setReleaseDate(LocalDate.of(2017, 12, 15));
        movie9.setTitle("Star Wars: The Last Jedi (Episode VIII)");
        movie9.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        movie9.setGenre(genreRepository.findByName(GenreNameEnum.SCI_FI));


        Picture picture9 = new Picture();
        picture9.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1691914078/eqenbrc6gwaeckslcymz.jpg");
        picture9.setPublicId("eqenbrc6gwaeckslcymz");
        picture9.setTitle("Star Wars: The Last Jedi (Episode VIII)");
        picture9.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture9.setMovie(movie9);

        movieRepository.save(movie9);
        pictureRepository.save(picture9);


        Movie movie10 = new Movie();
        movie10.setApproved(null);
        movie10.setDescription("Board the Millennium Falcon and journey to a galaxy far, far away in Solo: A Star Wars Story, an all-new adventure with the most beloved scoundrel in the galaxy. Through a series of daring escapades deep within a dark and dangerous criminal underworld, Han Solo meets his mighty future copilot Chewbacca and encounters the notorious gambler Lando Calrissian, in a journey that will set the course of one of the Star Wars saga’s most unlikely heroes.  ");
        movie10.setReleaseDate(LocalDate.of(2018, 5, 25));
        movie10.setTitle("Solo: A Star Wars Story");
        movie10.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        movie10.setGenre(genreRepository.findByName(GenreNameEnum.SCI_FI));


        Picture picture10 = new Picture();
        picture10.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1691914520/dklcxklnrfeqbn21pgdk.jpg");
        picture10.setPublicId("dklcxklnrfeqbn21pgdk");
        picture10.setTitle("Solo: A Star Wars Story");
        picture10.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture10.setMovie(movie10);

        movieRepository.save(movie10);
        pictureRepository.save(picture10);


        Movie movie11 = new Movie();
        movie11.setApproved(null);
        movie11.setDescription("Lucasfilm and director J.J. Abrams join forces once  more to take viewers on an epic journey to a galaxy far, far away with Star Wars: The Rise of Skywalker, the riveting conclusion of the  landmark Skywalker saga, in which new legends will be born and the final battle for freedom is yet to come.  ");
        movie11.setReleaseDate(LocalDate.of(2019, 12, 20));
        movie11.setTitle("Star Wars: The Rise of Skywalker (Episode IX)");
        movie11.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        movie11.setGenre(genreRepository.findByName(GenreNameEnum.SCI_FI));


        Picture picture11 = new Picture();
        picture11.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1691914256/c7s0qlwyb0mg8gzllalb.jpg");
        picture11.setPublicId("c7s0qlwyb0mg8gzllalb");
        picture11.setTitle("Star Wars: The Rise of Skywalker (Episode IX)");
        picture11.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture11.setMovie(movie11);

        movieRepository.save(movie11);
        pictureRepository.save(picture11);
    }
}
