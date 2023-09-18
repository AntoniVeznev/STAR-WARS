package com.example.star_wars_project.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "games")
public class Game extends BaseEntity {
    private String title;
    private String description;
    private String videoUrl;
    private LocalDate releaseDate;
    private Platform platform;
    private User author;
    private Boolean isApproved;

    public Game() {
    }

    @Column()
    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    @ManyToOne
    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    @Column(length = 100000, unique = true, nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Column(length = 100000, unique = true, nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Column(name = "video_url", nullable = false)
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Column(name = "release_date", nullable = false)
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @ManyToOne
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}