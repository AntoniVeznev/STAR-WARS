package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.binding.GameAddBindingModel;
import com.example.star_wars_project.model.binding.NewsAddBindingModel;
import com.example.star_wars_project.model.entity.*;
import com.example.star_wars_project.model.view.AllNewsViewModel;
import com.example.star_wars_project.repository.*;
import com.example.star_wars_project.service.CloudinaryService;
import com.example.star_wars_project.service.NewsService;
import com.example.star_wars_project.utils.CloudinaryImage;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {
    private final ModelMapper modelMapper;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final PictureRepository pictureRepository;
    private final GameRepository gameRepository;
    private final PlatformRepository platformRepository;
    private final CloudinaryService cloudinaryService;

    public NewsServiceImpl(ModelMapper modelMapper, NewsRepository newsRepository, UserRepository userRepository, PictureRepository pictureRepository, GameRepository gameRepository, PlatformRepository platformRepository, CloudinaryService cloudinaryService) {
        this.modelMapper = modelMapper;
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
        this.pictureRepository = pictureRepository;
        this.gameRepository = gameRepository;
        this.platformRepository = platformRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public void addNews(NewsAddBindingModel newsAddBindingModel, String currentUserUsername) throws IOException {
        News news = modelMapper.map(newsAddBindingModel, News.class);
        news.setAuthor(userRepository.findUserByUsername(currentUserUsername).orElse(null));
        newsRepository.save(news);

        MultipartFile pictureMultipartFile = newsAddBindingModel.getPicture();
        String pictureMultipartFileTitle = newsAddBindingModel.getPictureTitle();
        final CloudinaryImage uploaded = cloudinaryService.upload(pictureMultipartFile);
        Picture picture = new Picture();

        picture.setPictureUrl(uploaded.getUrl());
        picture.setPublicId(uploaded.getPublicId());

        picture.setTitle(pictureMultipartFileTitle);
        picture.setAuthor(userRepository.findUserByUsername(currentUserUsername).orElse(null));

        picture.setNews(newsRepository.findNewsByTitle(newsAddBindingModel.getTitle()));
        pictureRepository.save(picture);
    }

    @Override
    public List<AllNewsViewModel> latestStarWarsNews() {
        return newsRepository
                .findLatestThreeNews()
                .stream()
                .map(currentNews -> {
                    AllNewsViewModel latestNews =
                            modelMapper.map(currentNews, AllNewsViewModel.class);
                    Picture pictureBySerialId =
                            pictureRepository.findPictureByNews_Id(currentNews.getId());
                    latestNews.setPicture(pictureBySerialId);
                    return latestNews;
                }).collect(Collectors.toList());
    }

    @Override
    public News findNews(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    @Override
    public List<AllNewsViewModel> findAllNews() {

        return newsRepository
                .findAllNewsOrderedByNewestToOldest()
                .stream()
                .map(news -> {
                    AllNewsViewModel currentNews = modelMapper.map(news, AllNewsViewModel.class);
                    Picture pictureByNewsId = pictureRepository.findPictureByNews_Id(news.getId());
                    currentNews.setPicture(pictureByNewsId);
                    currentNews.setAuthorName(news.getAuthor().getUsername());
                    return currentNews;
                }).collect(Collectors.toList());
    }


}
