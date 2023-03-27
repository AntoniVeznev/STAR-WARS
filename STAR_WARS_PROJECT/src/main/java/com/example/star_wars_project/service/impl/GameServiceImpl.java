package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.binding.GameAddBindingModel;
import com.example.star_wars_project.model.entity.Game;
import com.example.star_wars_project.model.entity.Picture;
import com.example.star_wars_project.model.entity.Platform;
import com.example.star_wars_project.model.view.AllGamesViewModel;
import com.example.star_wars_project.repository.GameRepository;
import com.example.star_wars_project.repository.PictureRepository;
import com.example.star_wars_project.repository.PlatformRepository;
import com.example.star_wars_project.repository.UserRepository;
import com.example.star_wars_project.service.CloudinaryService;
import com.example.star_wars_project.service.GameService;
import com.example.star_wars_project.utils.CloudinaryImage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;
    private final PictureRepository pictureRepository;
    private final CloudinaryService cloudinaryService;
    private final UserRepository userRepository;
    private final PlatformRepository platformRepository;

    public GameServiceImpl(GameRepository gameRepository, ModelMapper modelMapper, PictureRepository pictureRepository, CloudinaryService cloudinaryService, UserRepository userRepository, PlatformRepository platformRepository) {
        this.gameRepository = gameRepository;
        this.modelMapper = modelMapper;
        this.pictureRepository = pictureRepository;
        this.cloudinaryService = cloudinaryService;
        this.userRepository = userRepository;
        this.platformRepository = platformRepository;
    }

    @Override
    public List<AllGamesViewModel> findAllGamesOrderedByReleaseDate() {
        return gameRepository
                .findAllGamesByReleaseDate()
                .stream()
                .map(game -> {
                    AllGamesViewModel currentGame = modelMapper.map(game, AllGamesViewModel.class);
                    Picture pictureByGameId = pictureRepository.findPictureByGame_Id(game.getId());
                    currentGame.setPicture(pictureByGameId);
                    return currentGame;
                }).collect(Collectors.toList());
    }

    @Override
    public Game findGame(Long id) {
        return gameRepository.findById(id).orElse(null);
    }

    @Override
    public void addGame(GameAddBindingModel gameAddBindingModel, String currentUserUsername) throws IOException {
        Game game = modelMapper.map(gameAddBindingModel, Game.class);
        game.setAuthor(userRepository.findUserByUsername(currentUserUsername).orElse(null));


        game.setPlatform(platformRepository.findPlatformByName(gameAddBindingModel.getPlatform()));

        gameRepository.save(game);

        MultipartFile pictureMultipartFile = gameAddBindingModel.getPicture();
        String pictureMultipartFileTitle = gameAddBindingModel.getPictureTitle();
        final CloudinaryImage uploaded = cloudinaryService.upload(pictureMultipartFile);

        Picture picture = new Picture();
        picture.setPictureUrl(uploaded.getUrl());
        picture.setPublicId(uploaded.getPublicId());

        picture.setTitle(pictureMultipartFileTitle);
        picture.setAuthor(userRepository.findUserByUsername(currentUserUsername).orElse(null));
        picture.setGame(gameRepository.findGameByTitle(gameAddBindingModel.getTitle()));
        pictureRepository.save(picture);

    }
}
