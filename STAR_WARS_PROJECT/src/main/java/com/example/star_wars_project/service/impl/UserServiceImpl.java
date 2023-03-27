package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.entity.Role;
import com.example.star_wars_project.model.entity.User;
import com.example.star_wars_project.model.service.UserServiceModel;
import com.example.star_wars_project.repository.RoleRepository;
import com.example.star_wars_project.repository.UserRepository;
import com.example.star_wars_project.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultPassForAdmin;
    private final String defaultPassForUser;


    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                           @Value("${spring.STAR_WARS_PROJECT.admin.defaultPasswordForAdmin}") String defaultPassForAdmin,
                           @Value("${spring.STAR_WARS_PROJECT.user.defaultPasswordForUser}") String defaultPassForUser) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.defaultPassForAdmin = defaultPassForAdmin;
        this.defaultPassForUser = defaultPassForUser;
    }


    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        User user = modelMapper.map(userServiceModel, User.class);
        List<Role> allRolesFromDb = roleRepository.findAll();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(allRolesFromDb.get(0));
        user.setRoles(userRoles);
        user.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
        userRepository.save(user);
        return modelMapper.map(user, UserServiceModel.class);
    }


    @Override
    public UserServiceModel findUserByUsernameAndPassword(String username, String password) {
        return userRepository
                .findUserByUsernameAndPassword(username, password)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }


    @Override
    public boolean checkUsername(UserServiceModel userServiceModel) {
        User userByUsername = userRepository.findUserByUsername(userServiceModel.getUsername()).orElse(null);
        if (userByUsername != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean checkEmail(UserServiceModel userServiceModel) {
        User userByEmail = userRepository.findUserByEmail(userServiceModel.getEmail()).orElse(null);
        if (userByEmail != null) {
            return true;
        }
        return false;
    }

    @Override
    public void initAdminAndUser() {
        if (userRepository.count() > 0) {
            return;
        }

        User admin = new User();
        admin.setUsername("Admin");
        admin.setFullName("Admin Adminov");
        admin.setEmail("Admin@gmail.com");
        admin.setPassword(passwordEncoder.encode(defaultPassForAdmin));

        Set<Role> adminRoles = new HashSet<>();
        List<Role> allRolesFromDb = roleRepository.findAll();
        adminRoles.add(allRolesFromDb.get(0));
        adminRoles.add(allRolesFromDb.get(1));
        admin.setRoles(adminRoles);

        userRepository.save(admin);

        User user = new User();
        user.setUsername("Vomer");
        user.setFullName("Antoni Veznev");
        user.setEmail("Vomer_1989@abv.bg");
        user.setPassword(passwordEncoder.encode(defaultPassForUser));

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(allRolesFromDb.get(0));
        user.setRoles(userRoles);

        userRepository.save(user);
    }
}