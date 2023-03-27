package com.example.star_wars_project.repository;

import com.example.star_wars_project.model.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    News findNewsByTitle(String title);

    @Query("select n from News n order by n.postDate desc limit 3")
    List<News> findLatestThreeNews();



    @Query("SELECT n from News n order by n.postDate desc ")
    List<News> findAllNewsOrderedByNewestToOldest();
}
