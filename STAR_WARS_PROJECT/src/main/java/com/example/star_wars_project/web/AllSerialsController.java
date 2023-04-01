package com.example.star_wars_project.web;

import com.example.star_wars_project.model.entity.Picture;
import com.example.star_wars_project.model.entity.Series;
import com.example.star_wars_project.model.view.AllSerialsViewModel;
import com.example.star_wars_project.service.PictureService;
import com.example.star_wars_project.service.SeriesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
            return "index";
        }
        return "serial-details";
    }
}
