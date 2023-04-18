package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.entity.Picture;
import com.example.star_wars_project.repository.PictureRepository;
import com.example.star_wars_project.service.PictureService;
import org.springframework.stereotype.Service;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;

    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Picture findPictureByMovieId(Long id) {
        return pictureRepository.findPictureByMovie_Id(id);
    }

    @Override
    public Picture findPictureBySerialId(Long id) {
        return pictureRepository.findPictureBySeries_Id(id);
    }

    @Override
    public Picture findPictureByNewsId(Long id) {
        return pictureRepository.findPictureByNews_Id(id);
    }

    @Override
    public Picture findPictureByGameId(Long id) {
        return pictureRepository.findPictureByGame_Id(id);
    }
}
