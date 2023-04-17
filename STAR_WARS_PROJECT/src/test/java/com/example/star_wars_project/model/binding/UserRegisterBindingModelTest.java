package com.example.star_wars_project.model.binding;


import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.junit.jupiter.api.Assertions.*;

public class UserRegisterBindingModelTest {

    private final LocalValidatorFactoryBean validatorFactory = new LocalValidatorFactoryBean();

    @Test
    void validUserRegisterBindingModel() {
        UserRegisterBindingModel user = new UserRegisterBindingModel();
        user.setUsername("johndoe");
        user.setFullName("John Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("password");
        user.setConfirmPassword("password");

        Errors errors = new BeanPropertyBindingResult(user, "user");
        validatorFactory.validate(user, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    void invalidEmailUserRegisterBindingModel() {
        UserRegisterBindingModel user = new UserRegisterBindingModel();
        user.setUsername("johndoe");
        user.setFullName("John Doe");
        user.setEmail("johndoexample.com");
        user.setPassword("password");
        user.setConfirmPassword("password");

        Errors errors = new BeanPropertyBindingResult(user, "user");
        validatorFactory.validate(user, errors);

        assertFalse(errors.hasErrors());
        assertEquals(0, errors.getFieldErrors("email").size());
    }

    @Test
    void invalidPasswordUserRegisterBindingModel() {
        UserRegisterBindingModel user = new UserRegisterBindingModel();
        user.setUsername("johndoe");
        user.setFullName("John Doe");
        user.setEmail("johndoe@example.com");
        user.setPassword("pw");
        user.setConfirmPassword("pw");

        Errors errors = new BeanPropertyBindingResult(user, "user");
        validatorFactory.validate(user, errors);

        assertFalse(errors.hasErrors());
        assertEquals(0, errors.getFieldErrors("password").size());
    }

}
