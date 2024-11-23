package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.services.MediaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("medias/mediaTypes")
public class MediaTypeController {

    private final MediaTypeService mediaTypeService;

    @Autowired
    public MediaTypeController(MediaTypeService mediaTypeService) {
        this.mediaTypeService = mediaTypeService;
    }
}
