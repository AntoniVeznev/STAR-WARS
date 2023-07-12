package com.example.star_wars_project.model.binding;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import static org.junit.jupiter.api.Assertions.*;

public class UserRegisterBindingModelTest {

    private final LocalValidatorFactoryBean validatorFactory = new LocalValidatorFactoryBean();

    @Mock
    UserRegisterBindingModel userRegisterBindingModelTest=new UserRegisterBindingModel();


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

    @Test
    void testGetUsername(){
        userRegisterBindingModelTest.setUsername("Ivan");
        String username = userRegisterBindingModelTest.getUsername();
        Assertions.assertEquals("Ivan", username);
    }
    @Test
    void testGetFullName(){
        userRegisterBindingModelTest.setFullName("Antoni Veznev");
        String fullName = userRegisterBindingModelTest.getFullName();
        Assertions.assertEquals("Antoni Veznev", fullName);
    }
    @Test
    void testEmail(){
        userRegisterBindingModelTest.setEmail("kaligula_1989@abv.bg");
        String testEmail = userRegisterBindingModelTest.getEmail();
        Assertions.assertEquals("kaligula_1989@abv.bg", testEmail);
    }
    @Test
    void testGetPassword(){
        userRegisterBindingModelTest.setPassword("123456");
        String testPassword = userRegisterBindingModelTest.getPassword();
        Assertions.assertEquals("123456", testPassword);
    }
    @Test
    void testGetConfirmPassword(){
        userRegisterBindingModelTest.setConfirmPassword("123456");
        String testConfPassword = userRegisterBindingModelTest.getConfirmPassword();
        Assertions.assertEquals("123456", testConfPassword);
    }
}
