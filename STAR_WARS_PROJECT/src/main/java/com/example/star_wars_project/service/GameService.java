package com.example.star_wars_project.service;

import com.example.star_wars_project.model.binding.GameAddBindingModel;
import com.example.star_wars_project.model.entity.Game;
import com.example.star_wars_project.model.view.AllGamesViewModel;

import java.io.IOException;
import java.util.List;

public interface GameService {
    List<AllGamesViewModel> findAllGamesOrderedByReleaseDate();

    Game findGame(Long id);

    void addGame(GameAddBindingModel gameAddBindingModel, String currentUserUsername) throws IOException;

    List<AllGamesViewModel> findAllGamesWithValueNullOrFalse();

    void approveGameWithId(Long id);

    void deleteGameWithId(Long id);

    void initGames();
}