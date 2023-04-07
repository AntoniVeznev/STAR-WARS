package com.example.star_wars_project.repository;

import com.example.star_wars_project.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findMovieByTitle(String title);

    @Query("SELECT m FROM Movie m order by m.releaseDate asc")
    List<Movie> findAllMoviesByReleaseDate();

    @Query("select m from Movie m order by m.releaseDate desc limit 4")
    List<Movie> findNewestFourMoviesByReleaseDate();

    @Query("select m from Movie m where m.approved = null or m.approved=false")
    List<Movie> findMoviesThatAreNotApproved();

    Movie findMovieById(Long id);
}