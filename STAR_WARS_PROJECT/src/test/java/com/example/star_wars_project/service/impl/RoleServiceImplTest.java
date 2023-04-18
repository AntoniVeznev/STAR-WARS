package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.entity.Role;
import com.example.star_wars_project.model.entity.enums.RoleNameEnum;
import com.example.star_wars_project.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class RoleServiceImplTest {
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    void testInitRoles() {

        when(roleRepository.count()).thenReturn(0L);

        roleService.initRoles();

        verify(roleRepository, times(RoleNameEnum.values().length)).save(any(Role.class));
    }
}
