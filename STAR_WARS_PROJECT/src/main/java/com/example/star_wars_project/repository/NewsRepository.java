package com.example.star_wars_project.repository;

import com.example.star_wars_project.model.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    News findNewsByTitle(String title);

    @Query("select n from News n order by n.postDate desc limit 3")
    List<News> findLatestThreeNews();

    @Query("SELECT n from News n order by n.postDate desc ")
    List<News> findAllNewsOrderedByNewestToOldest();

    @Query("select n from News n where n.approved = null or n.approved=false")
    List<News> findNewsThatAreNotApproved();

    News findNewsById(Long id);

    List<News> findAllByPostDateBefore(LocalDateTime dateAndTimeNow);
}