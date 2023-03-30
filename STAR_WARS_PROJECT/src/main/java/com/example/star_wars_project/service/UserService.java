package com.example.star_wars_project.service;

import com.example.star_wars_project.model.service.UserServiceModel;
import com.example.star_wars_project.model.view.AllUsersViewModel;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findUserByUsernameAndPassword(String username, String password);



    boolean checkUsername(UserServiceModel userServiceModel);

    boolean checkEmail(UserServiceModel userServiceModel);


    void initAdminAndUser();

    List<AllUsersViewModel> findAllUsersWithRoleUSER();



    void deleteUserWithId(Long id);

    void promoteUserWithId(Long id);
}
