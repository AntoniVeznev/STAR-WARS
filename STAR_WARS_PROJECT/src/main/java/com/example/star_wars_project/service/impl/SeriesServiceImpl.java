package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.binding.SeriesAddBindingModel;
import com.example.star_wars_project.model.entity.Picture;
import com.example.star_wars_project.model.entity.Series;
import com.example.star_wars_project.model.view.AllSerialsViewModel;
import com.example.star_wars_project.repository.GenreRepository;
import com.example.star_wars_project.repository.PictureRepository;
import com.example.star_wars_project.repository.SeriesRepository;
import com.example.star_wars_project.repository.UserRepository;

import com.example.star_wars_project.utils.CloudinaryImage;
import com.example.star_wars_project.service.CloudinaryService;
import com.example.star_wars_project.service.SeriesService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesServiceImpl implements SeriesService {
    private final SeriesRepository seriesRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final PictureRepository pictureRepository;

    public SeriesServiceImpl(SeriesRepository seriesRepository, UserRepository userRepository, GenreRepository genreRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService, PictureRepository pictureRepository) {
        this.seriesRepository = seriesRepository;
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public List<AllSerialsViewModel> findAllSerialsOrderedByReleaseDate() {
        //todo order by release date  return list with serial view models:)
        return seriesRepository
                .findAllSeriesByReleaseDate()
                .stream()
                .map(series -> {
                    AllSerialsViewModel currentSerial = modelMapper.map(series, AllSerialsViewModel.class);
                    Picture pictureBySerialId = pictureRepository.findPictureBySeries_Id(series.getId());
                    currentSerial.setPicture(pictureBySerialId);
                    return currentSerial;
                }).collect(Collectors.toList());

    }

    @Override
    public List<AllSerialsViewModel> latestStarWarsSerials() {
        return seriesRepository
                .findNewestFourSeriesByReleaseDate()
                .stream()
                .map(newestSerial -> {
                    AllSerialsViewModel serial =
                            modelMapper.map(newestSerial, AllSerialsViewModel.class);
                    Picture pictureBySerialId =
                            pictureRepository.findPictureBySeries_Id(newestSerial.getId());
                    serial.setPicture(pictureBySerialId);
                    return serial;
                }).collect(Collectors.toList());
    }

    @Override
    public Series findSerial(Long id) {
        return seriesRepository.findById(id).orElse(null);
    }

    @Override
    public void addSerial(SeriesAddBindingModel seriesAddBindingModel, String currentUserUsername) throws IOException {
        Series series = modelMapper.map(seriesAddBindingModel, Series.class);
        series.setAuthor(userRepository.findUserByUsername(currentUserUsername).orElse(null));

        series.setGenre(genreRepository.findByName(seriesAddBindingModel.getGenre()));

        seriesRepository.save(series);

        MultipartFile pictureMultipartFile = seriesAddBindingModel.getPicture();
        String pictureMultipartFileTitle = seriesAddBindingModel.getPictureTitle();
        final CloudinaryImage uploaded = cloudinaryService.upload(pictureMultipartFile);

        Picture picture = new Picture();
        picture.setPictureUrl(uploaded.getUrl());
        picture.setPublicId(uploaded.getPublicId());

        picture.setTitle(pictureMultipartFileTitle);
        picture.setAuthor(userRepository.findUserByUsername(currentUserUsername).orElse(null));
        picture.setSeries(seriesRepository.findSeriesByTitle(seriesAddBindingModel.getTitle()));
        pictureRepository.save(picture);
    }
}
