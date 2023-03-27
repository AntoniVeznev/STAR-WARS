package com.example.star_wars_project.service;

import com.example.star_wars_project.model.service.UserServiceModel;

public interface UserService {

    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findUserByUsernameAndPassword(String username, String password);



    boolean checkUsername(UserServiceModel userServiceModel);

    boolean checkEmail(UserServiceModel userServiceModel);


    void initAdminAndUser();
}
