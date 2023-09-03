package com.example.star_wars_project.initialization;

import com.example.star_wars_project.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBaseInit implements CommandLineRunner {
    public final RoleService roleService;
    public final GenreService genreService;
    public final PlatformService platformService;
    public final UserService userService;
    public final MovieService movieService;
    public final SeriesService seriesService;

    public final NewsService newsService;
    public final GameService gameService;

    public DataBaseInit(RoleService roleService, GenreService genreService, PlatformService platformService, UserService userService, MovieService movieService, SeriesService seriesService, NewsService newsService, GameService gameService) {
        this.roleService = roleService;
        this.genreService = genreService;
        this.platformService = platformService;
        this.userService = userService;
        this.movieService = movieService;
        this.seriesService = seriesService;
        this.newsService = newsService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) {
        roleService.initRoles();
        genreService.initGenres();
        platformService.initPlatforms();
        userService.initAdminAndUser();

        movieService.initMovies();
        seriesService.initSeries();
        newsService.initNews();
        gameService.initGames();
    }
}