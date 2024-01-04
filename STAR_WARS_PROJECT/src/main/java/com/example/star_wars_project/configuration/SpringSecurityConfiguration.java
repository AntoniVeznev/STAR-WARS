package com.example.star_wars_project.configuration;

import com.example.star_wars_project.model.entity.enums.RoleNameEnum;
import com.example.star_wars_project.repository.UserRepository;
import com.example.star_wars_project.service.impl.ApplicationUserDetailServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/videos/**", "/webjars/**", "/api/**").permitAll()
                        .requestMatchers("/", "/movies/catalogue", "/news/catalogue", "/series/catalogue", "/games/catalogue", "/users/login-error").permitAll()
                        .requestMatchers("/users/login", "/users/register").anonymous()
                        .requestMatchers("/users/logout").authenticated()
                        .requestMatchers("/admin").hasRole(RoleNameEnum.ADMINISTRATOR.name())
                        .anyRequest()
                        .authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/users/login").permitAll()
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/")
                        .failureForwardUrl("/users/login-error")
                )
                .logout((logout) -> logout
                        .logoutUrl("/users/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                );
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new ApplicationUserDetailServiceImpl(userRepository);
    }
}
