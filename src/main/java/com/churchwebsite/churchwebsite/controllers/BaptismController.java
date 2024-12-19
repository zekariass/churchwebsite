package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.services.BaptismService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("services/baptism")
public class BaptismController {

    private final BaptismService baptismService;

    @Autowired
    public BaptismController(BaptismService baptismService) {
        this.baptismService = baptismService;
    }
}
