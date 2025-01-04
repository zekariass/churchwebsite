package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.AboutUs;
import com.churchwebsite.churchwebsite.services.AboutUsService;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/aboutUs")
public class PublicAboutUsController {
    private final AboutUsService aboutUsService;
    private final ChurchDetailService churchDetailService;

    private final String PUBLIC_CONTENT = "layouts/base";


    @Autowired
    public PublicAboutUsController(AboutUsService aboutUsService, ChurchDetailService churchDetailService) {
        this.aboutUsService = aboutUsService;
        this.churchDetailService = churchDetailService;
    }

    @GetMapping("")
    public String showAboutUsPage(Model model){

        List<AboutUs> aboutUsList = aboutUsService.getAllAboutUs();

        model.addAttribute("aboutUsList", aboutUsList);
        model.addAttribute("activeContentPage", "about-us");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return PUBLIC_CONTENT;
    }

}
