package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.dtos.ChurchDetailDTO;
import com.churchwebsite.churchwebsite.entities.ChurchContact;
import com.churchwebsite.churchwebsite.entities.ContactUs;
import com.churchwebsite.churchwebsite.services.ChurchContactService;
import com.churchwebsite.churchwebsite.services.ChurchDetailService;
import com.churchwebsite.churchwebsite.services.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/contactUs")
public class ContactUsController {

    private final ContactUsService contactUsService;
    private final ChurchDetailService churchDetailService;
    private final ChurchContactService churchContactService;

    private final String PUBLIC_CONTENT = "layouts/base";

    @Autowired
    public ContactUsController(ContactUsService contactUsService,
                               ChurchDetailService churchDetailService,
                               ChurchContactService churchContactService) {
        this.contactUsService = contactUsService;
        this.churchDetailService = churchDetailService;
        this.churchContactService = churchContactService;
    }

    @GetMapping("")
    public String showContactOptions(Model model){

        model.addAttribute("activeContentPage", "contact-us-options");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("contactUs", new ContactUs());
        model.addAttribute("churchContacts", churchContactService.findAllContacts());

        return PUBLIC_CONTENT;
    }
}
