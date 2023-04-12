package com.example.star_wars_project.configuration;

import com.example.star_wars_project.interceptor.TimeInterceptor;
import com.example.star_wars_project.interceptor.TotalLoginsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    private final TimeInterceptor timeInterceptor;
    private final TotalLoginsInterceptor totalLoginsInterceptor;

    public InterceptorConfiguration(TimeInterceptor timeInterceptor, TotalLoginsInterceptor totalLoginsInterceptor) {
        this.timeInterceptor = timeInterceptor;
        this.totalLoginsInterceptor = totalLoginsInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor)
                .excludePathPatterns("/css/**", "/images/**", "/js/**", "/videos/**", "/webjars/**", "/api/**");
        registry.addInterceptor(totalLoginsInterceptor)
                .excludePathPatterns("/css/**", "/images/**", "/js/**", "/videos/**", "/webjars/**", "/api/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
