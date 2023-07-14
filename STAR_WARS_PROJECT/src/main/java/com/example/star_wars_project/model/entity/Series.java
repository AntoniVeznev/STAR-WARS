package com.example.star_wars_project.model.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "series")
public class Series extends BaseEntity {
    private String title;
    private String description;
    private LocalDate releaseDate;
    private User author;
    private Genre genre;
    private Boolean isApproved;

    public Series() {
    }

    @Column()
    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    @ManyToOne
    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
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