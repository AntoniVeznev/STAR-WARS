package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.binding.SeriesAddBindingModel;
import com.example.star_wars_project.model.entity.Picture;
import com.example.star_wars_project.model.entity.Series;
import com.example.star_wars_project.model.entity.enums.GenreNameEnum;
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
import java.time.LocalDate;
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

    @Override
    public List<AllSerialsViewModel> findAllSeriesWithValueNullOrFalse() {
        return seriesRepository
                .findSeriesThatAreNotApproved()
                .stream()
                .map(series -> {
                    AllSerialsViewModel currentSerial = modelMapper.map(series, AllSerialsViewModel.class);
                    Picture pictureBySeriesId = pictureRepository.findPictureBySeries_Id(series.getId());
                    currentSerial.setPicture(pictureBySeriesId);
                    return currentSerial;
                }).collect(Collectors.toList());
    }

    @Override
    public void approveSerialWithId(Long id) {
        Series series = seriesRepository.findSerialById(id);
        series.setApproved(true);
        seriesRepository.save(series);
    }

    @Override
    public void deleteSerialWithId(Long id) {
        List<Picture> allBySerialId = pictureRepository.findAllBySeries_Id(id);
        pictureRepository.deleteAll(allBySerialId);
        seriesRepository.deleteById(id);
    }

    @Override
    public void initSeries() {
        if (seriesRepository.count() > 0) {
            return;
        }

        Series series1 = new Series();
        series1.setApproved(null);
        series1.setDescription("In an era filled with danger, deception and intrigue, Cassian Andor will discover the difference he can make in the struggle against the tyrannical Galactic Empire. He embarks on a path that is destined to turn him into a rebel hero.");
        series1.setReleaseDate(LocalDate.of(2022, 9, 21));
        series1.setTitle("Andor");
        series1.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        series1.setGenre(genreRepository.findByName(GenreNameEnum.CRIME));


        Picture picture1 = new Picture();
        picture1.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680193752/Andor_l5emwa.webp");
        picture1.setPublicId("Andor_l5emwa");
        picture1.setTitle("Andor");
        picture1.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture1.setSeries(series1);

        seriesRepository.save(series1);
        pictureRepository.save(picture1);


        Series series2 = new Series();
        series2.setApproved(null);
        series2.setDescription("Jedi Master Obi-Wan Kenobi has to save young Leia after she is kidnapped, all the while being pursued by Imperial Inquisitors and his former Padawan, now known as Darth Vader.");
        series2.setReleaseDate(LocalDate.of(2022, 5, 27));
        series2.setTitle("Obi-Wan Kenobi");
        series2.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        series2.setGenre(genreRepository.findByName(GenreNameEnum.ADVENTURE));


        Picture picture2 = new Picture();
        picture2.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680193759/Obi-Wan_Kenobi_xr4qtd.webp");
        picture2.setPublicId("Obi-Wan_Kenobi_xr4qtd");
        picture2.setTitle("Obi-Wan Kenobi");
        picture2.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture2.setSeries(series2);

        seriesRepository.save(series2);
        pictureRepository.save(picture2);


        Series series3 = new Series();
        series3.setApproved(null);
        series3.setDescription("The legendary bounty hunter Boba Fett navigates the underworld of the galaxy with mercenary Fennec Shand when they return to the sands of Tatooine to stake their claim on the territory formerly ruled by the deceased crime lord Jabba the Hutt.");
        series3.setReleaseDate(LocalDate.of(2021, 12, 29));
        series3.setTitle("The Book of Boba Fett");
        series3.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        series3.setGenre(genreRepository.findByName(GenreNameEnum.CRIME));


        Picture picture3 = new Picture();
        picture3.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680193768/The_Book_of_Boba_Fett_ep6pbn.webp");
        picture3.setPublicId("The_Book_of_Boba_Fett_ep6pbn");
        picture3.setTitle("The Book of Boba Fett");
        picture3.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture3.setSeries(series3);

        seriesRepository.save(series3);
        pictureRepository.save(picture3);


        Series series4 = new Series();
        series4.setApproved(null);
        series4.setDescription("The Star Wars saga continues from Executive Producer George Lucas and Lucasfilm Animation! With cutting-edge, feature-film quality computer animation, classic characters, astounding action, and the timeless battle between good and evil, Star Wars: The Clone Wars expands the Star Wars story with all new adventures set in a galaxy far, far away.");
        series4.setReleaseDate(LocalDate.of(2008, 8, 15));
        series4.setTitle("The Clone Wars");
        series4.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        series4.setGenre(genreRepository.findByName(GenreNameEnum.ANIMATION));


        Picture picture4 = new Picture();
        picture4.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680193774/The_Clone_Wars_iiguxr.jpg");
        picture4.setPublicId("The_Clone_Wars_iiguxr");
        picture4.setTitle("The Clone Wars");
        picture4.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture4.setSeries(series4);

        seriesRepository.save(series4);
        pictureRepository.save(picture4);
    }
}