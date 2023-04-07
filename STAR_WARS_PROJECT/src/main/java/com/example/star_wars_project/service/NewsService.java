package com.example.star_wars_project.service;

import com.example.star_wars_project.model.binding.NewsAddBindingModel;
import com.example.star_wars_project.model.entity.News;
import com.example.star_wars_project.model.view.AllNewsViewModel;

import java.io.IOException;
import java.util.List;

public interface NewsService {
    void addNews(NewsAddBindingModel newsAddBindingModel, String currentUserUsername) throws IOException;

    List<AllNewsViewModel> latestStarWarsNews();

    News findNews(Long id);

    List<AllNewsViewModel> findAllNews();

    List<AllNewsViewModel> findAllNewsWithValueNullOrFalse();

    void approveNewsWithId(Long id);

    void deleteNewsWithId(Long id);

    void initNews();

    void deleteOlderNews();
}