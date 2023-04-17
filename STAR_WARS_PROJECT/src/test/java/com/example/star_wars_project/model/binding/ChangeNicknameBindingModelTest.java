package com.example.star_wars_project.model.binding;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ChangeNicknameBindingModelTest {

    @Test
    public void testSetAndGetUsername() {
        String expectedUsername = "myUsername";
        ChangeNicknameBindingModel model = new ChangeNicknameBindingModel();
        model.setUsername(expectedUsername);
        String actualUsername = model.getUsername();
        Assertions.assertEquals(expectedUsername, actualUsername);
    }
}
