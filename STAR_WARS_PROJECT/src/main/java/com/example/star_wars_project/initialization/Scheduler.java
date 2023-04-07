package com.example.star_wars_project.initialization;

import com.example.star_wars_project.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    private final NewsService newsService;
    private final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);

    public Scheduler(NewsService newsService) {
        this.newsService = newsService;
    }

    @Scheduled(fixedRate = 20000)
    public void doInBackground() {
        newsService.deleteOlderNews();
        LOGGER.info("Deleting all news in DATABASE that are older than 3 months!");
    }
}