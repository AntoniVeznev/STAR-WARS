package com.example.star_wars_project.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.security.Principal;

import com.example.star_wars_project.model.binding.ChangeNicknameBindingModel;
import com.example.star_wars_project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private UserService userService;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private UserController userController;
    private Principal principal;
    private Model model;

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

    @Test
    void testPostProfileWithCorrectData() throws Exception {
        ChangeNicknameBindingModel changeNicknameBindingModel = mock(ChangeNicknameBindingModel.class);
        BindingResult bindingResult = mock(BindingResult.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        String viewName = userController.postProfile(changeNicknameBindingModel, bindingResult, redirectAttributes, principal);
        assertEquals("logout-now", viewName);
        mockMvc
                .perform(post("/users/profile")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testLoginGet() throws Exception {
        mockMvc
                .perform(get("/users/login"))
                .andExpect(status().isOk());
    }

    @Test
    void testRegisterGet() throws Exception {
        Model model = mock(Model.class);
        when(model.containsAttribute("usernameNotExist")).thenReturn(true);
        when(model.containsAttribute("emailNotExist")).thenReturn(true);
        mockMvc.perform(get("/users/register")
        ).andExpect(status().isOk());
    }
}