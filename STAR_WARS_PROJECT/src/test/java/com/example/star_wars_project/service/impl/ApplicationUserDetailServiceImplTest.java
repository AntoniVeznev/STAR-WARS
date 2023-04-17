package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.entity.Role;
import com.example.star_wars_project.model.entity.User;
import com.example.star_wars_project.model.entity.enums.RoleNameEnum;
import com.example.star_wars_project.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationUserDetailServiceImplTest {
    private final String NOT_EXISTING_USERNAME = "Pesho";
    private ApplicationUserDetailServiceImpl toTest;
    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        toTest = new ApplicationUserDetailServiceImpl(
                mockUserRepository
        );
    }

    @Test
    void testUserFound() {
        Role testAdminRole = new Role();
        testAdminRole.setName(RoleNameEnum.ADMINISTRATOR);
        Role testUserRole = new Role();
        testUserRole.setName(RoleNameEnum.USER);

        User testUser = new User();
        String EXISTING_USERNAME = "Vomer";
        testUser.setUsername(EXISTING_USERNAME);
        testUser.setPassword("topsecret");
        testUser.setRoles(Set.of(testAdminRole, testUserRole));
        when(mockUserRepository.findUserByUsername(EXISTING_USERNAME))
                .thenReturn(Optional.of(testUser));
        UserDetails adminDetails = toTest.loadUserByUsername(EXISTING_USERNAME);

        Assertions.assertNotNull(adminDetails);
        Assertions.assertEquals(EXISTING_USERNAME, adminDetails.getUsername());
        Assertions.assertEquals(testUser.getPassword(), adminDetails.getPassword());
        Assertions.assertEquals(2, adminDetails.getAuthorities().size());
        assertRole(adminDetails.getAuthorities(), "ROLE_ADMINISTRATOR");
        assertRole(adminDetails.getAuthorities(), "ROLE_USER");
    }

    private void assertRole(Collection<? extends GrantedAuthority> authorities, String role) {
        authorities
                .stream()
                .filter(a ->
                        role.equals(a.getAuthority())).findAny()
                .orElseThrow(() -> new AssertionFailedError("Role " + role + " not found!"));
    }

    @Test
    void testUserNotFound() {
        assertThrows(UsernameNotFoundException.class,
                () -> {
                    toTest.loadUserByUsername(NOT_EXISTING_USERNAME);
                });
    }
}
