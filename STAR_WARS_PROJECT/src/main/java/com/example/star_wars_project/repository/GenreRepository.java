package com.example.star_wars_project.repository;

import com.example.star_wars_project.model.entity.Genre;
import com.example.star_wars_project.model.entity.enums.GenreNameEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByName(GenreNameEnum name);
}