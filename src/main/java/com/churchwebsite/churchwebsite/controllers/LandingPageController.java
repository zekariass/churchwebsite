package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.dtos.ChurchDetailDTO;
import com.churchwebsite.churchwebsite.entities.LandingContent;
import com.churchwebsite.churchwebsite.entities.News;
import com.churchwebsite.churchwebsite.services.LandingContentService;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LandingPageController {

    private final ChurchDetailService churchDetailService;
    private final LandingContentService landingContentService;

    @Autowired
    public LandingPageController(ChurchDetailService churchDetailService,
                                 LandingContentService landingContentService) {
        this.churchDetailService = churchDetailService;
        this.landingContentService = landingContentService;
    }

    @GetMapping("/")
    public String landingPage(Model model){
        ChurchDetailDTO churchDetail = churchDetailService.getChurchDetail();

        List<LandingContent> landingContentList = landingContentService.findAll();

        model.addAttribute("churchDetail", churchDetail);
        model.addAttribute("landingContentList", landingContentList);
        model.addAttribute("activeContentPage", "landing-page");



        return "layouts/base";
    }

}
