package com.example.star_wars_project.initialization;

import com.example.star_wars_project.service.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SchedulerTest {
    @Mock
    private NewsService newsService;
    @Mock
    private Logger logger;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDoInBackgroundDeletesOlderNews() {
        Scheduler scheduler = new Scheduler(newsService);
        scheduler.doInBackground();
        verify(newsService, times(1)).deleteOlderNews();
    }

    @Test
    public void testScheduledJobRunsEvery20Seconds() {
        ScheduledAnnotationBeanPostProcessor postProcessor = new ScheduledAnnotationBeanPostProcessor();
        postProcessor.setBeanFactory(Mockito.mock(BeanFactory.class));
        Scheduler scheduler = new Scheduler(newsService);
        postProcessor.postProcessAfterInitialization(scheduler, "scheduler");
    }
}