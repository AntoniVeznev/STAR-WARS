package com.example.star_wars_project.service;

import com.example.star_wars_project.model.binding.SeriesAddBindingModel;
import com.example.star_wars_project.model.entity.Series;
import com.example.star_wars_project.model.view.AllSerialsViewModel;

import java.io.IOException;
import java.util.List;

public interface SeriesService {
    List<AllSerialsViewModel> findAllSerialsOrderedByReleaseDate();

    List<AllSerialsViewModel> latestStarWarsSerials();

    Series findSerial(Long id);

    void addSerial(SeriesAddBindingModel seriesAddBindingModel, String currentUserUsername) throws IOException;

    List<AllSerialsViewModel> findAllSeriesWithValueNullOrFalse();

    void approveSerialWithId(Long id);

    void deleteSerialWithId(Long id);

    void initSeries();
}