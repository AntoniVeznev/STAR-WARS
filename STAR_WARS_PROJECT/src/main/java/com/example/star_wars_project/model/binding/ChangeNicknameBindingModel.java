package com.example.star_wars_project.model.binding;

import org.hibernate.validator.constraints.Length;

public class ChangeNicknameBindingModel {
    private String username;

    public ChangeNicknameBindingModel() {
    }

    @Length(min = 3, max = 20, message = "Username length must be between 3 and 20 characters! Please enter a valid username!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
