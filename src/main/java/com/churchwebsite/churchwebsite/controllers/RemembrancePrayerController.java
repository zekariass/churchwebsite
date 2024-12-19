package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.services.RemembrancePrayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("services/remembrance-prayer-requests")
public class RemembrancePrayerController {
    private final RemembrancePrayerService remembrancePrayerService;

    @Autowired
    public RemembrancePrayerController(RemembrancePrayerService remembrancePrayerService) {
        this.remembrancePrayerService = remembrancePrayerService;
    }
}
