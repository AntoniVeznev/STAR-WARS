package com.example.star_wars_project.repository;

import com.example.star_wars_project.model.entity.Game;
import com.example.star_wars_project.model.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Game findGameByTitle(String title);

    @Query("SELECT g FROM Game g order by g.releaseDate asc")
    List<Game> findAllGamesByReleaseDate();

}
