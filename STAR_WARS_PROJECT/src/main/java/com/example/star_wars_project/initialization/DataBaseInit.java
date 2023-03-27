package com.example.star_wars_project.initialization;

import com.example.star_wars_project.service.GenreService;
import com.example.star_wars_project.service.PlatformService;
import com.example.star_wars_project.service.RoleService;
import com.example.star_wars_project.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataBaseInit implements CommandLineRunner {
    private final RoleService roleService;
    private final GenreService genreService;
    private final PlatformService platformService;
    private final UserService userService;


    public DataBaseInit(RoleService roleService, GenreService genreService, PlatformService platformService, UserService userService) {
        this.roleService = roleService;
        this.genreService = genreService;
        this.platformService = platformService;
        this.userService = userService;
    }

    @Override
    public void run(String... args)  {
        roleService.initRoles();
        genreService.initGenres();
        platformService.initPlatforms();
        userService.initAdminAndUser();
    }
}
