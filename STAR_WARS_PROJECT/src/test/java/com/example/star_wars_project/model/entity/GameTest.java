package com.example.star_wars_project.model.entity;

import com.example.star_wars_project.model.entity.enums.PlatformNameEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.setTitle("Super Mario Bros");
        game.setDescription("A classic platformer game");
        game.setVideoUrl("https://www.youtube.com/watch?v=7qID2UE8KxE");
        game.setReleaseDate(LocalDate.of(1985, 9, 13));
        game.setApproved(true);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("Super Mario Bros", game.getTitle());
        assertEquals("A classic platformer game", game.getDescription());
        assertEquals("https://www.youtube.com/watch?v=7qID2UE8KxE", game.getVideoUrl());
        assertEquals(LocalDate.of(1985, 9, 13), game.getReleaseDate());
        assertTrue(game.getApproved());
    }

    @Test
    void testSetPlatform() {
        Platform platform = new Platform();
        platform.setName(PlatformNameEnum.PC);
        game.setPlatform(platform);
        assertEquals(PlatformNameEnum.PC, game.getPlatform().getName());
    }

    @Test
    void testSetAuthor() {
        User user = new User();
        user.setUsername("JohnDoe");
        game.setAuthor(user);
        assertEquals("JohnDoe", game.getAuthor().getUsername());
    }
}