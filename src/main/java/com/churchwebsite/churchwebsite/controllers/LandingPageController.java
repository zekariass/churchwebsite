package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.dtos.OrganisationDetailDTO;
import com.churchwebsite.churchwebsite.entities.LandingContent;
import com.churchwebsite.churchwebsite.entities.Organisation;
import com.churchwebsite.churchwebsite.entities.OrganisationBanner;
import com.churchwebsite.churchwebsite.services.LandingContentService;
import com.churchwebsite.churchwebsite.services.OrganisationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LandingPageController {

    private final OrganisationDetailService organisationDetailService;
    private final LandingContentService landingContentService;

    @Autowired
    public LandingPageController(OrganisationDetailService organisationDetailService,
                                 LandingContentService landingContentService) {
        this.organisationDetailService = organisationDetailService;
        this.landingContentService = landingContentService;
    }

    @GetMapping("/")
    public String landingPage(Model model){
        OrganisationDetailDTO organisationDetail = organisationDetailService.getOrganisationDetail();

        List<LandingContent> landingContentList = landingContentService.findAll();

        model.addAttribute("organisationDetail", organisationDetail);
        model.addAttribute("landingContentList", landingContentList);
        model.addAttribute("activeContentPage", "landing-page");

        return "layouts/base";
    }

}
