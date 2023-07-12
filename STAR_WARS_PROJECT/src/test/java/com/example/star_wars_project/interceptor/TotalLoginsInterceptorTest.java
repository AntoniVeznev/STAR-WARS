package com.example.star_wars_project.interceptor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import static org.mockito.Mockito.*;

public class TotalLoginsInterceptorTest {

    @Mock
    private ModelAndView modelAndView;

    private TotalLoginsInterceptor totalLoginsInterceptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        totalLoginsInterceptor = new TotalLoginsInterceptor();
    }

    @Test
    public void testPostHandleIncrementsTotalLogins() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteUser("user1");

        MockHttpServletResponse response = new MockHttpServletResponse();

        totalLoginsInterceptor.postHandle(request, response, null, modelAndView);
        verify(modelAndView, never()).addObject(anyString(), any());


        request.setRemoteUser("user2");
        totalLoginsInterceptor.postHandle(request, response, null, modelAndView);
        verify(modelAndView, never()).addObject(anyString(), any());


        totalLoginsInterceptor.postHandle(request, response, null, modelAndView);
        verify(modelAndView, never()).addObject(anyString(), any());


        assert totalLoginsInterceptor.totalLoginCounts == 2;
    }
    @Test
    public void testPreHandle() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        totalLoginsInterceptor.preHandle(request,response,null);

    }
}
