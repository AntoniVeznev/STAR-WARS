package com.example.star_wars_project.model.binding;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class NewsAddBindingModel {
    private String title;
    private LocalDateTime postDate;
    private String description;
    private String pictureTitle;
    private MultipartFile picture;
    private String publicId;

    public NewsAddBindingModel() {
    }

    @Length(min = 3, max = 250, message = "News title length must be between 3 and 250 characters!")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @DateTimeFormat(pattern = "yyyy-MM-dd'T 'HH:mm")
    @PastOrPresent(message = "Post date can't be in the future!")
    @NotNull(message = "Post date can't be empty! Please enter a date!")
    public LocalDateTime getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }
    @Length(min = 50, max = 10000, message = "Description length must be between 10 and 10000 characters!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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