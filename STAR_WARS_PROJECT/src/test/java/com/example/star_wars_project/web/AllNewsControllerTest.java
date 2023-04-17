package com.example.star_wars_project.web;

import com.example.star_wars_project.exception.ItemNotFoundException;

import com.example.star_wars_project.model.view.AllNewsViewModel;
import com.example.star_wars_project.service.NewsService;
import com.example.star_wars_project.service.PictureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AllNewsControllerTest {

    @Mock
    private NewsService newsService;

    @Mock
    private PictureService pictureService;

    @InjectMocks
    private AllNewsController allNewsController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(allNewsController)
                .setControllerAdvice(new ItemNotFoundException())
                .build();
    }


    @Test
    void testAllNewsWithNoNews() throws Exception {

        List<AllNewsViewModel> allNews = new ArrayList<>();

        given(newsService.findAllNews()).willReturn(allNews);


        mockMvc.perform(get("/news/catalogue"))


                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }


    @Test
    void testDetailsWithInvalidNewsId() throws Exception {

        Long newsId = 1L;
        given(newsService.findNews(newsId)).willReturn(null);


        mockMvc.perform(get("/news/details/{id}", newsId))
                .andExpect(status().isNotFound())
                .andExpect(view().name("other-errors/news-not-found"));
    }

    @Test
    void testOnNewsNotFound() {
        ItemNotFoundException exception = new ItemNotFoundException();

        ModelAndView modelAndView = allNewsController.onNewsNotFound(exception);

        assertEquals("other-errors/news-not-found", modelAndView.getViewName());
    }
}
