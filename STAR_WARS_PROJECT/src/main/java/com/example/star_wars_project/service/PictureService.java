package com.example.star_wars_project.service;

import com.example.star_wars_project.model.entity.Picture;

public interface PictureService {
    Picture findPictureByMovieId(Long id);

    Picture findPictureBySerialId(Long id);

    Picture findPictureByNewsId(Long id);

    Picture findPictureByGameId(Long id);
}