package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.services.AttachmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("medias/mediaTypes")
public class AttachmentTypeController {

    private final AttachmentTypeService attachmentTypeService;

    @Autowired
    public AttachmentTypeController(AttachmentTypeService attachmentTypeService) {
        this.attachmentTypeService = attachmentTypeService;
    }
}
