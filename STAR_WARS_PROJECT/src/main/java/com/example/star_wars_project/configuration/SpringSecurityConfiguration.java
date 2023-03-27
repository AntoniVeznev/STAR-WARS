package com.example.star_wars_project.configuration;

import com.example.star_wars_project.model.entity.enums.RoleNameEnum;
import com.example.star_wars_project.repository.UserRepository;
import com.example.star_wars_project.service.impl.ApplicationUserDetailServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests()
                //TODO: РАЗРЕШАВАМ УЖ ПАПКА СТАТИК!
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                //TODO: НАИСТИНА РАЗРЕШАВАМ ПАПКА СТАТИК!
                .requestMatchers("/css/**", "/images/**", "/js/**", "/videos/**", "/webjars/**").permitAll()
                //TODO: ДОСТЪПНО КАКТО ЗА АНОНИМНИ ТАКА И ЗА ЛОГНАТИ БЕЗ ЗНАЧЕНИЕ АДМИН ИЛИ ЮЗАР!
                .requestMatchers("/", "/movies/catalogue", "/news/catalogue", "/series/catalogue", "/games/catalogue", "/users/login-error").permitAll()
                //TODO: РАЗРЕШАВАМ АНОНИМНИТЕ ДА МОГАТ ДА СЕ ЛОГВАТ И РЕГИСТРИРАТ!
                .requestMatchers("/users/login", "/users/register").anonymous()
                //TODO: РАЗРЕШАВАМ ЛОГНАТИТЕ ЮЗАРИ И АДМИНИ ДА МОГАТ ДА СЕ ЛОГАУТВАТ!
                .requestMatchers("/users/logout").authenticated()
                //TODO: РАЗРЕШАВАМ ДОСТЪП НА АДМИНА ДО АДМИН СТРАНИЦАТА МУ!
                .requestMatchers("/users/admin").hasRole(RoleNameEnum.ADMINISTRATOR.name())

                .anyRequest()
                .authenticated()

                .and()

                .formLogin()
                .loginPage("/users/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/")
                .failureForwardUrl("/users/login-error")

                .and()

                .logout()
                .logoutUrl("/users/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);

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
