package com.example.star_wars_project.repository;

import com.example.star_wars_project.model.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Long> {
    Picture findPictureByMovie_Id(Long id);

    Picture findPictureBySeries_Id(Long id);

    Picture findPictureByNews_Id(Long id);

    Picture findPictureByGame_Id(Long id);

    List<Picture> findAllByMovie_Id(Long id);

    List<Picture> findAllByNews_Id(Long id);

    List<Picture> findAllByGame_Id(Long id);

    List<Picture> findAllBySeries_Id(Long id);
}