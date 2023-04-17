package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.entity.News;
import com.example.star_wars_project.repository.NewsRepository;
import com.example.star_wars_project.repository.PictureRepository;
import com.example.star_wars_project.repository.UserRepository;
import com.example.star_wars_project.service.CloudinaryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class NewsServiceImplTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PictureRepository pictureRepository;

    @Mock
    private CloudinaryService cloudinaryService;

    @InjectMocks
    private NewsServiceImpl newsService;


    @Test
    void findNews() {
        Long newsId = 1L;
        News news = new News();
        given(newsRepository.findById(newsId)).willReturn(Optional.of(news));

        News foundNews = newsService.findNews(newsId);

        assertEquals(news, foundNews);
    }
}
