package com.example.star_wars_project.interceptor;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeInterceptorTest {
    @Test
    public void testPreHandle() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        Object handler = new Object();
        HandlerInterceptor interceptor = new TimeInterceptor();

        boolean result = interceptor.preHandle(request, response, handler);

        assertEquals(true, result);
        assertEquals(true, request.getAttribute("startTime") instanceof Long);
    }
}