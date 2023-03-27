package com.example.star_wars_project.model.binding;

import com.example.star_wars_project.model.entity.Platform;
import com.example.star_wars_project.model.entity.enums.PlatformNameEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class GameAddBindingModel {
    private String title;
    private LocalDate releaseDate;
    private String description;
    private String videoUrl;
    private PlatformNameEnum platform;
    private String pictureTitle;
    private MultipartFile picture;
    private String publicId;

    public GameAddBindingModel() {
    }

    @Length(min = 3, max = 250, message = "Game title length must be between 3 and 250 characters!")
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

    @Length(min = 10, max = 3000, message = "Description length must be between 10 and 1000 characters!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "Video link cant be empty!")
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
    @NotNull
    public PlatformNameEnum getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformNameEnum platform) {
        this.platform = platform;
    }

    @Length(min = 4, max = 100, message = "Please insert picture title with length between 4 and 100 characters!")
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
}
