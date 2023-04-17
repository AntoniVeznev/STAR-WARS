package com.example.star_wars_project.model.entity;

import com.example.star_wars_project.model.entity.enums.GenreNameEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenreTest {

    @Test
    void testSetNameAndGetGenreNameEnum() {
        Genre genre = new Genre();
        genre.setName(GenreNameEnum.ACTION);
        assertEquals(GenreNameEnum.ACTION, genre.getName());
    }
}
