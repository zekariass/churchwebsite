package com.churchwebsite.churchwebsite.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping("/home")
    public String dashboard(Model model){
        model.addAttribute("activeDashPage", "dash-landing");
        return "dashboard/dash-fragments/dash-main-panel";
    }

    @GetMapping("/messages")
    public String messages(Model model){
        model.addAttribute("activeDashPage", "messages");
        return "dashboard/dash-fragments/dash-main-panel";
    }

    @GetMapping("/events")
    public String events(Model model){
        model.addAttribute("activeDashPage", "events");
        return "dashboard/dash-fragments/dash-main-panel";
    }




}
