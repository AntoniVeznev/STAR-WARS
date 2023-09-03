package com.example.star_wars_project.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;
import java.util.stream.Collectors;

import com.example.star_wars_project.model.entity.enums.RoleNameEnum;
import com.example.star_wars_project.model.view.AllUsersViewModel;
import com.example.star_wars_project.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.star_wars_project.model.entity.Role;
import com.example.star_wars_project.model.entity.User;
import com.example.star_wars_project.model.service.UserServiceModel;
import com.example.star_wars_project.repository.RoleRepository;
import com.example.star_wars_project.repository.UserRepository;

public class UserServiceImplTest {
    private UserServiceImpl userService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private CommentRepository commentRepository;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;


    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        modelMapper = new ModelMapper();
        userService = new UserServiceImpl(modelMapper, userRepository, roleRepository, commentRepository, passwordEncoder, "defaultPassForAdmin", "defaultPassForUser");
    }

    @Test
    void testRegisterUser() {

        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername("testuser");
        userServiceModel.setFullName("Test User");
        userServiceModel.setEmail("testuser@example.com");
        userServiceModel.setPassword("testpassword");

        User user = new User();
        user.setUsername(userServiceModel.getUsername());
        user.setFullName(userServiceModel.getFullName());
        user.setEmail(userServiceModel.getEmail());
        user.setPassword(userServiceModel.getPassword());

        List<Role> roles = new ArrayList<Role>();
        Role role = new Role();
        role.setName(RoleNameEnum.USER);
        roles.add(role);
        when(roleRepository.findAll()).thenReturn(roles);
        when(passwordEncoder.encode(userServiceModel.getPassword())).thenReturn("testpassword");
        when(userRepository.save(user)).thenReturn(user);

        UserServiceModel result = userService.registerUser(userServiceModel);

        assertEquals(userServiceModel.getUsername(), result.getUsername());
        assertEquals(userServiceModel.getFullName(), result.getFullName());
        assertEquals(userServiceModel.getEmail(), result.getEmail());
        assertEquals(userServiceModel.getPassword(), result.getPassword());
    }

    @Test
    void checkUsername_WithExistingUsername_ShouldReturnTrue() {

        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername("testUser");

        User user = new User();
        user.setUsername("testUser");

        Mockito.when(userRepository.findUserByUsername("testUser")).thenReturn(Optional.of(user));

        boolean result = userService.checkUsername(userServiceModel);

        assertTrue(result);
    }

    @Test
    void checkUsername_WithNonExistingUsername_ShouldReturnFalse() {

        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername("testUser");

        Mockito.when(userRepository.findUserByUsername(any())).thenReturn(Optional.empty());

        boolean result = userService.checkUsername(userServiceModel);

        assertFalse(result);
    }


    @Test
    void testCheckEmail_withExistingEmail_shouldReturnTrue() {

        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setEmail("test@example.com");
        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(Optional.of(user));

        boolean result = userService.checkEmail(userServiceModel);

        assertTrue(result);
        verify(userRepository, times(1)).findUserByEmail("test@example.com");
    }

    @Test
    void testCheckEmail_withNonExistingEmail_shouldReturnFalse() {

        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setEmail("test@example.com");
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(Optional.empty());

        boolean result = userService.checkEmail(userServiceModel);

        assertFalse(result);
        verify(userRepository, times(1)).findUserByEmail("test@example.com");
    }

    @Test
    public void testFindAllUsersWithRoleUSER() {

        User user1 = new User();
        user1.setUsername("user1");
        user1.setEmail("user1@example.com");
        Set<Role> roles1 = new HashSet<Role>();
        Role role = new Role();
        role.setName(RoleNameEnum.USER);
        roles1.add(role);
        user1.setRoles(roles1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setEmail("user2@example.com");
        Set<Role> roles2 = new HashSet<Role>();
        Role role2 = new Role();
        role2.setName(RoleNameEnum.USER);
        roles2.add(role2);
        user2.setRoles(roles2);

        User user3 = new User();
        user3.setUsername("user3");
        user3.setEmail("user3@example.com");
        Set<Role> roles3 = new HashSet<Role>();

        Role role3 = new Role();
        role3.setName(RoleNameEnum.USER);
        roles3.add(role3);

        Role role4 = new Role();
        role4.setName(RoleNameEnum.ADMINISTRATOR);
        roles3.add(role4);

        user3.setRoles(roles3);

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2, user3));

        List<AllUsersViewModel> result = userService.findAllUsersWithRoleUSER();

        assertEquals(2, result.size());
        List<String> usernames = result.stream().map(AllUsersViewModel::getUsername).collect(Collectors.toList());
        assertEquals(Arrays.asList("user1", "user2"), usernames);
    }
}
