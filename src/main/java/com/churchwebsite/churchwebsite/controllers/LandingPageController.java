package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.Announcement;
import com.churchwebsite.churchwebsite.entities.EmailSubscription;
import com.churchwebsite.churchwebsite.entities.LandingContent;
import com.churchwebsite.churchwebsite.entities.News;
import com.churchwebsite.churchwebsite.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LandingPageController {

    private final ChurchDetailService churchDetailService;
    private final LandingContentService landingContentService;

    private final NewsService newsService;
    private final BlogService blogService;
    private final AnnouncementService announcementService;

    @Autowired
    public LandingPageController(ChurchDetailService churchDetailService,
                                 LandingContentService landingContentService, NewsService newsService, BlogService blogService, AnnouncementService announcementService) {
        this.churchDetailService = churchDetailService;
        this.landingContentService = landingContentService;
        this.newsService = newsService;
        this.blogService = blogService;
        this.announcementService = announcementService;
    }

    @GetMapping("/")
    public String landingPage(Model model){
//        ChurchDetailDTO churchDetail = churchDetailService.getChurchDetail();

        List<LandingContent> landingContentList = landingContentService.findAll();

//        model.addAttribute("churchDetail", churchDetail);
        model.addAttribute("landingContentList", landingContentList);
        model.addAttribute("activeContentPage", "landing-page");
        model.addAttribute("emailSubscription", new EmailSubscription());
        model.addAttribute("pageTitle", "Home Page");

        List<News> newsList = newsService.findByArchivedAndActiveAndFeatured(false, true, true);
        List<Announcement> announcements = announcementService.findByArchivedAndActiveAndFeatured(false, true, true);

        model.addAttribute("newsList", newsList);
        model.addAttribute("announcements", announcements);
        return "layouts/base";
    }

}
