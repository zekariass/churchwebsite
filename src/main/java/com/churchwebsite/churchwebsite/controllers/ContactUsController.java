package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.ContactUs;
import com.churchwebsite.churchwebsite.services.*;
import com.churchwebsite.churchwebsite.utils.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/contactUs")
public class ContactUsController {

    private final ContactUsService contactUsService;
    private final CustomUserDetailService customUserDetailService;
    private final UserService userService;
    private final ChurchDetailService churchDetailService;
    private final ChurchContactService churchContactService;

    private final String PUBLIC_CONTENT = "layouts/base";

    @Autowired
    public ContactUsController(ContactUsService contactUsService, CustomUserDetailService customUserDetailService, UserService userService,
                               ChurchDetailService churchDetailService,
                               ChurchContactService churchContactService) {
        this.contactUsService = contactUsService;
        this.customUserDetailService = customUserDetailService;
        this.userService = userService;
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

    @PostMapping("")
    public String processContactUsForm(@Valid @ModelAttribute ContactUs contactUs,
                                       BindingResult result,
                                       RedirectAttributes redirectAttributes,
                                       Model model){

        if(result.hasErrors()){
            model.addAttribute("activeContentPage", "contact-us-options");
            model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
            model.addAttribute("contactUs", contactUs);
            model.addAttribute("churchContacts", churchContactService.findAllContacts());

            return PUBLIC_CONTENT;
        }

        CustomUserDetails customUserDetails = userService.getCurrentUser();

        contactUs.setUser(null);
        if(customUserDetails != null){
            contactUs.setUser(customUserDetails.getUser());
        }
        contactUs.setRead(false);

        contactUsService.save(contactUs);

        redirectAttributes.addFlashAttribute("message", "Your message is submitted successfully.");
        return "redirect:/contactUs";
    }
}
