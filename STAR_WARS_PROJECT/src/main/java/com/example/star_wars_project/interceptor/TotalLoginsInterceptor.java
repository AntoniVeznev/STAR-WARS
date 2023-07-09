package com.example.star_wars_project.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Component
public class TotalLoginsInterceptor implements HandlerInterceptor {
    int totalLoginCounts = 0;
    String oldLoggedInUser = "";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (request.getRemoteUser() != null) {
            if (!Objects.equals(request.getRemoteUser(), oldLoggedInUser)) {
                totalLoginCounts += 1;
                oldLoggedInUser = request.getRemoteUser();
                System.out.println("The total number of logins since the website has been active: " + totalLoginCounts);
            }
        }
    }
}
