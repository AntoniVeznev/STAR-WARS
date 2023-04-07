package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.entity.Role;
import com.example.star_wars_project.model.entity.User;
import com.example.star_wars_project.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class ApplicationUserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public ApplicationUserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findUserByUsername(username)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found!"));
    }

    private UserDetails map(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                extractAuthorities(user)
        );
    }

    private List<GrantedAuthority> extractAuthorities(User user) {
        return user
                .getRoles()
                .stream()
                .map(this::mapRole)
                .toList();
    }

    private GrantedAuthority mapRole(Role role) {
        return new SimpleGrantedAuthority("ROLE_" + role.getName().name());
    }
}