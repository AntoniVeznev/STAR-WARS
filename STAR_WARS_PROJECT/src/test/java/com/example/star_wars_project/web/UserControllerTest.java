package com.example.star_wars_project.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.security.Principal;

import com.example.star_wars_project.model.binding.ChangeNicknameBindingModel;
import com.example.star_wars_project.model.binding.UserRegisterBindingModel;

import com.example.star_wars_project.service.UserService;

import jakarta.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    private UserController userController;


    @Mock
    UserRegisterBindingModel userRegisterBindingModel;

    @Mock
    RedirectAttributes redirectAttributes;

    @Mock
    Model model;

    private Validator validator;

    private Principal principal;

    @BeforeEach
    void setUp() {
        principal = mock(Principal.class);
        when(principal.getName()).thenReturn("currentUserName");
    }

    @Test
    void testGetProfile() {
        Model model = mock(Model.class);
        String viewName = userController.getProfile(principal, model);
        assertEquals("profile", viewName);
        verify(model).addAttribute(eq("currentName"), eq("currentUserName"));
    }

    @Test
    void testPostProfileWithBindingErrors() {
        ChangeNicknameBindingModel changeNicknameBindingModel = mock(ChangeNicknameBindingModel.class);
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userController.postProfile(changeNicknameBindingModel, bindingResult, redirectAttributes, principal);

        assertEquals("redirect:profile", viewName);
        verify(redirectAttributes).addFlashAttribute(eq("changeNicknameBindingModel"), eq(changeNicknameBindingModel));
        verify(redirectAttributes).addFlashAttribute(eq("org.springframework.validation.BindingResult.changeNicknameBindingModel"), eq(bindingResult));
        verifyNoInteractions(userService, modelMapper);
    }
}
