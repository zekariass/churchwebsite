package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final ChurchDetailService churchDetailService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";


    @Autowired
    public DashboardController(ChurchDetailService churchDetailService) {
        this.churchDetailService = churchDetailService;
    }

    @GetMapping("/home")
    public String dashboard(Model model){
        model.addAttribute("activeDashPage", "dash-landing");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/messages")
    public String messages(Model model){
        model.addAttribute("activeDashPage", "messages");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/events")
    public String events(Model model){
        model.addAttribute("activeDashPage", "events");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        return DASHBOARD_MAIN_PANEL;
    }




}
