package com.example.star_wars_project.repository;

import com.example.star_wars_project.model.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {
    Series findSeriesByTitle(String title);

    @Query("SELECT s from Series s order by s.releaseDate asc")
    List<Series> findAllSeriesByReleaseDate();

    @Query("select s from Series s order by s.releaseDate desc limit 4")
    List<Series> findNewestFourSeriesByReleaseDate();

    @Query("select s from Series s where s.approved = null or s.approved=false")
    List<Series> findSeriesThatAreNotApproved();

    Series findSerialById(Long id);
}