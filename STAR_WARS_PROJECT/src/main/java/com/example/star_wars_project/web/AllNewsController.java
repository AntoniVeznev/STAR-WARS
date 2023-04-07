package com.example.star_wars_project.web;

import com.example.star_wars_project.exception.ItemNotFoundException;
import com.example.star_wars_project.model.entity.News;
import com.example.star_wars_project.model.entity.Picture;
import com.example.star_wars_project.model.entity.User;
import com.example.star_wars_project.model.view.AllNewsViewModel;
import com.example.star_wars_project.service.NewsService;
import com.example.star_wars_project.service.PictureService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/news")
public class AllNewsController {
    private final NewsService newsService;
    private final PictureService pictureService;

    public AllNewsController(NewsService newsService, PictureService pictureService) {
        this.newsService = newsService;
        this.pictureService = pictureService;
    }

    @GetMapping("/catalogue")
    public String allNews(Model model) {
        AllNewsViewModel news = null;
        List<AllNewsViewModel> allNews = newsService.findAllNews();
        if (allNews.size() > 0) {
            news = allNews.get(0);
            model.addAttribute("news", news);
        } else {
            return "redirect:/";
        }
        model.addAttribute("allNews", allNews);
        return "news-catalogue";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        News currentNews = newsService.findNews(id);
        Picture picture = pictureService.findPictureByNewsId(id);

        model.addAttribute("currentNews", currentNews);
        model.addAttribute("picture", picture);

        if (currentNews == null) {
            throw new ItemNotFoundException();
        }

        User author = currentNews.getAuthor();
        model.addAttribute("author", author);

        return "news-details";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException.class)
    public ModelAndView onNewsNotFound(ItemNotFoundException infe) {
        return new ModelAndView("other-errors/news-not-found");
    }
}