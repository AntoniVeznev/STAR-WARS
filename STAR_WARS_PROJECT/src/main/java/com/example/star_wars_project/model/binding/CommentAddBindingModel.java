package com.example.star_wars_project.model.binding;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class CommentAddBindingModel {
    private LocalDateTime created;
    private String postContent;

    public CommentAddBindingModel() {

    }

    @DateTimeFormat(pattern = "yyyy-MM-dd'T 'HH:mm")
    @FutureOrPresent(message = "Comment date must be in present!")
    @NotNull(message = "Comment date can't be empty! Please enter a date!")
    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Length(min = 3, max = 3000, message = "Comment length must be between 3 and 3000 characters!")
    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }
}