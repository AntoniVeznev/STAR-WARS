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

    @Length(min = 4, max = 250, message = "News title length must be between 4 and 250 characters!")
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

    @Length(min = 150, max = 100000, message = "Description length must be between 150 and 100000 characters!")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}