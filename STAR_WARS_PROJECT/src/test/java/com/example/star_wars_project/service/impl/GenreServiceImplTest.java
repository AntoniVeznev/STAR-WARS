package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.entity.enums.GenreNameEnum;
import com.example.star_wars_project.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GenreServiceImplTest {


    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreServiceImpl genreService;

    @Test
    public void testInitGenresWhenGenresExist() {

        given(genreRepository.count()).willReturn(1L);

        genreService.initGenres();

        verify(genreRepository, never()).save(any());
    }

    @Test
    public void testInitGenresWhenGenresDoNotExist() {

        given(genreRepository.count()).willReturn(0L);

        genreService.initGenres();

        verify(genreRepository, times(GenreNameEnum.values().length)).save(any());
    }
}


