package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.entity.Comment;
import com.example.star_wars_project.model.entity.Role;
import com.example.star_wars_project.model.entity.User;
import com.example.star_wars_project.model.service.UserServiceModel;
import com.example.star_wars_project.model.view.AllUsersViewModel;
import com.example.star_wars_project.repository.CommentRepository;
import com.example.star_wars_project.repository.RoleRepository;
import com.example.star_wars_project.repository.UserRepository;
import com.example.star_wars_project.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;
    private final String defaultPassForAdmin;
    private final String defaultPassForUser;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository, RoleRepository roleRepository, CommentRepository commentRepository, PasswordEncoder passwordEncoder,
                           @Value("${spring.STAR_WARS_PROJECT.admin.defaultPasswordForAdmin}") String defaultPassForAdmin,
                           @Value("${spring.STAR_WARS_PROJECT.user.defaultPasswordForUser}") String defaultPassForUser) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.commentRepository = commentRepository;
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

        initDefaultAdmin();

        initDefaultUser();

    }

    public void initDefaultAdmin() {
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
    }

    public void initDefaultUser() {
        User user = new User();
        user.setUsername("Vomer");
        user.setFullName("Antoni Veznev");
        user.setEmail("Vomer_1989@abv.bg");
        user.setPassword(passwordEncoder.encode(defaultPassForUser));

        Set<Role> userRoles = new HashSet<>();
        List<Role> allRolesFromDb = roleRepository.findAll();
        userRoles.add(allRolesFromDb.get(0));
        user.setRoles(userRoles);

        userRepository.save(user);
    }

    @Override
    public List<AllUsersViewModel> findAllUsersWithRoleUSER() {
        List<User> all = userRepository.findAll();
        List<AllUsersViewModel> allUsersViewModel = new ArrayList<>();
        for (User user : all) {
            if (user.getRoles().size() == 1) {
                allUsersViewModel.add(modelMapper.map(user, AllUsersViewModel.class));
            }
        }
        return allUsersViewModel;
    }

    @Override
    public void promoteUserWithId(Long id) {
        User user = userRepository.findUserById(id);
        user.getRoles().clear();

        List<Role> allRolesFromDb = roleRepository.findAll();
        user.getRoles().add(allRolesFromDb.get(0));
        user.getRoles().add(allRolesFromDb.get(1));

        userRepository.save(user);
    }

    @Override
    public void changeUsernameOfCurrentUser(String currentUserName, UserServiceModel userServiceModel) {
        String username = userServiceModel.getUsername();
        User userByUsername = userRepository.findUserByUsername(currentUserName).orElse(null);
        if (userByUsername != null) {
            userByUsername.setUsername(username);
            userRepository.save(userByUsername);
        }
    }


    @Override
    public void deleteUserWithId(Long id) {
        List<Comment> allByAuthorId = commentRepository.findCommentsByAuthor_Id(id);
        commentRepository.deleteAll(allByAuthorId);
        userRepository.deleteById(id);
    }
}