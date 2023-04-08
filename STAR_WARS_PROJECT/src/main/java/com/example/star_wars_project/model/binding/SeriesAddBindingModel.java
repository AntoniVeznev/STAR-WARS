package com.example.star_wars_project.model.binding;

import com.example.star_wars_project.model.entity.enums.GenreNameEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class SeriesAddBindingModel {
    private String title;
    private LocalDate releaseDate;
    private GenreNameEnum genre;
    private String description;
    private String pictureTitle;
    private MultipartFile picture;
    private String publicId;

    public SeriesAddBindingModel() {
    }

    @Length(min = 5, max = 100, message = "Picture title length must be between 5 and 100 characters!")
    public String getPictureTitle() {
        return pictureTitle;
    }

    public void setPictureTitle(String pictureTitle) {
        this.pictureTitle = pictureTitle;
    }

    public MultipartFile getPicture() {
        return picture;
    }

    public void setPicture(MultipartFile picture) {
        this.picture = picture;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    @Length(min = 4, max = 250, message = "Serial title length must be between 4 and 250 characters!")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Release date can't be in the future!")
    @NotNull(message = "Release date can't be empty! Please enter a date!")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @NotNull
    public GenreNameEnum getGenre() {
        return genre;
    }

    public void setGenre(GenreNameEnum genre) {
        this.genre = genre;
    }

    @Length(min = 50, max = 10000, message = "Description length must be between 50 and 10000 characters!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}