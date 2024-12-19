package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.services.HolyMatrimonyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("services/holy-matrimony")
public class HolyMatrimonyController {

    private final HolyMatrimonyService holyMatrimonyService;

    @Autowired
    public HolyMatrimonyController(HolyMatrimonyService holyMatrimonyService) {
        this.holyMatrimonyService = holyMatrimonyService;
    }
}
