package com.example.star_wars_project.initialization;

import com.example.star_wars_project.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBaseInit implements CommandLineRunner {
    private final RoleService roleService;
    private final GenreService genreService;
    private final PlatformService platformService;
    private final UserService userService;
    private final MovieService movieService;
    private final SeriesService seriesService;
    private final NewsService newsService;
    private final GameService gameService;

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