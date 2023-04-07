package com.example.star_wars_project.web;

import com.example.star_wars_project.exception.ItemNotFoundException;
import com.example.star_wars_project.model.entity.Picture;
import com.example.star_wars_project.model.entity.Series;
import com.example.star_wars_project.model.view.AllSerialsViewModel;
import com.example.star_wars_project.service.PictureService;
import com.example.star_wars_project.service.SeriesService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/series")
public class AllSerialsController {
    private final SeriesService seriesService;
    private final PictureService pictureService;

    public AllSerialsController(SeriesService seriesService, PictureService pictureService) {
        this.seriesService = seriesService;
        this.pictureService = pictureService;
    }

    @GetMapping("/catalogue")
    public String allSerials(Model model) {
        List<AllSerialsViewModel> serials = seriesService.findAllSerialsOrderedByReleaseDate();
        model.addAttribute("serials", serials);
        return "serials-catalogue";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        Series currentSerial = seriesService.findSerial(id);
        Picture picture = pictureService.findPictureBySerialId(id);
        model.addAttribute("currentSerial", currentSerial);
        model.addAttribute("picture", picture);
        if (currentSerial == null) {
            throw new ItemNotFoundException();
        }
        return "serial-details";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ItemNotFoundException.class)
    public ModelAndView onSerialNotFound(ItemNotFoundException infe) {
        return new ModelAndView("other-errors/serial-not-found");
    }
}