package com.example.star_wars_project.model.binding;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class CommentAddBindingModel {
    private LocalDateTime created;
    private String postContent;

    public CommentAddBindingModel() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentAddBindingModel that = (CommentAddBindingModel) o;
        return Objects.equals(created, that.created) && Objects.equals(postContent, that.postContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(created, postContent);
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

    @NotNull
    @Length(min = 4, max = 5000, message = "Comment length must be between 4 and 5000 characters!")
    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }
}