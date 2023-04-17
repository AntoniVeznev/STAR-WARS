package com.example.star_wars_project.model.view;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllUsersViewModelTest {

    @Test
    public void testGettersAndSetters() {
        AllUsersViewModel model = new AllUsersViewModel();
        Long id = 1L;
        String username = "john_doe";
        String fullName = "John Doe";
        String email = "john.doe@example.com";

        model.setId(id);
        model.setUsername(username);
        model.setFullName(fullName);
        model.setEmail(email);

        assertEquals(id, model.getId());
        assertEquals(username, model.getUsername());
        assertEquals(fullName, model.getFullName());
        assertEquals(email, model.getEmail());
    }
}