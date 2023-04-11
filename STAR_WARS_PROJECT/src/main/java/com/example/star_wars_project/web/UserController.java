package com.example.star_wars_project.web;

import com.example.star_wars_project.model.binding.ChangeNicknameBindingModel;
import com.example.star_wars_project.model.binding.UserRegisterBindingModel;
import com.example.star_wars_project.model.service.UserServiceModel;
import com.example.star_wars_project.service.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/profile")
    public String getProfile(Principal principal, Model model) {
        model.addAttribute("currentName", principal.getName());
        return "profile";
    }

    @PostMapping("/profile")
    public String postProfile(@Valid ChangeNicknameBindingModel changeNicknameBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {
        String currentUserName = principal.getName();

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("changeNicknameBindingModel", changeNicknameBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changeNicknameBindingModel", bindingResult);
            return "redirect:profile";
        }

        UserServiceModel userServiceModel = modelMapper.map(changeNicknameBindingModel, UserServiceModel.class);
        userService.changeUsernameOfCurrentUser(currentUserName, userServiceModel);
        return "logout-now";
    }

    @ModelAttribute
    public ChangeNicknameBindingModel changeNicknameBindingModel() {
        return new ChangeNicknameBindingModel();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("bad_credential", true);
        return "redirect:/users/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        if (model.containsAttribute("usernameNotExist")) {
            model.addAttribute("usernameNotExist", true);
        }
        if (model.containsAttribute("emailNotExist")) {
            model.addAttribute("emailNotExist", true);
        }
        return "register";
    }

    @ModelAttribute
    public UserRegisterBindingModel userRegisterBindingModel() {
        return new UserRegisterBindingModel();
    }

    @PostMapping("/register")
    public String registerConfirm(@Valid UserRegisterBindingModel userRegisterBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors() || !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {

            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            return "redirect:register";
        }

        boolean checkUsername = userService.checkUsername(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));
        boolean checkEmail = userService.checkEmail(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));

        if (checkUsername) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("usernameNotExist", false);
            return "redirect:register";
        }

        if (checkEmail) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("emailNotExist", false);
            return "redirect:register";
        }

        userService.registerUser(modelMapper.map(userRegisterBindingModel, UserServiceModel.class));

        return "redirect:login";
    }
}