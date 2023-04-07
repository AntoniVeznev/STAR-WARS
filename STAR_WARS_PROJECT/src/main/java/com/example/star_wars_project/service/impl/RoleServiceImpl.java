package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.entity.Role;
import com.example.star_wars_project.model.entity.enums.RoleNameEnum;
import com.example.star_wars_project.repository.RoleRepository;
import com.example.star_wars_project.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void initRoles() {

        if (roleRepository.count() > 0) {
            return;
        }

        Arrays.stream(RoleNameEnum.values()).forEach(roleNameEnum -> {
            Role role = new Role();
            role.setName(roleNameEnum);
            roleRepository.save(role);
        });
    }
}