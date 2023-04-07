package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.entity.Genre;
import com.example.star_wars_project.model.entity.enums.GenreNameEnum;
import com.example.star_wars_project.repository.GenreRepository;
import com.example.star_wars_project.service.GenreService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public void initGenres() {
        if (genreRepository.count() > 0) {
            return;
        }

        Arrays.stream(GenreNameEnum.values()).forEach(genreNameEnum -> {
            Genre genre = new Genre();
            genre.setName(genreNameEnum);
            genreRepository.save(genre);
        });
    }
}