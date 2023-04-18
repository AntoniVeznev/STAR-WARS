package com.example.star_wars_project.service.impl;


import com.example.star_wars_project.model.entity.*;
import com.example.star_wars_project.repository.PictureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PictureServiceImplTest {
    @Mock
    private PictureRepository pictureRepository;

    @InjectMocks
    private PictureServiceImpl pictureService;

    @Test
    public void testFindPictureByMovieId() {
        Long id = 1L;
        Picture picture = new Picture();
        Movie movie = new Movie();
        movie.setId(id);
        picture.setMovie(movie);

        Mockito.when(pictureRepository.findPictureByMovie_Id(id)).thenReturn(picture);

        Picture result = pictureService.findPictureByMovieId(id);

        Assertions.assertEquals(picture, result);
        Mockito.verify(pictureRepository, Mockito.times(1)).findPictureByMovie_Id(id);
    }

    @Test
    public void testFindPictureBySerialId() {
        Long id = 1L;
        Picture picture = new Picture();
        Series series = new Series();
        series.setId(id);
        picture.setSeries(series);

        Mockito.when(pictureRepository.findPictureBySeries_Id(id)).thenReturn(picture);

        Picture result = pictureService.findPictureBySerialId(id);

        Assertions.assertEquals(picture, result);
        Mockito.verify(pictureRepository, Mockito.times(1)).findPictureBySeries_Id(id);
    }

    @Test
    public void testFindPictureByNewsId() {
        Long id = 1L;
        Picture picture = new Picture();
        News news = new News();
        news.setId(id);
        picture.setNews(news);

        Mockito.when(pictureRepository.findPictureByNews_Id(id)).thenReturn(picture);

        Picture result = pictureService.findPictureByNewsId(id);

        Assertions.assertEquals(picture, result);
        Mockito.verify(pictureRepository, Mockito.times(1)).findPictureByNews_Id(id);
    }

    @Test
    public void testFindPictureByGameId() {
        Long id = 1L;
        Picture picture = new Picture();
        Game game = new Game();
        game.setId(id);
        picture.setGame(game);

        Mockito.when(pictureRepository.findPictureByGame_Id(id)).thenReturn(picture);

        Picture result = pictureService.findPictureByGameId(id);

        Assertions.assertEquals(picture, result);
        Mockito.verify(pictureRepository, Mockito.times(1)).findPictureByGame_Id(id);
    }

}
