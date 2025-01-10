package com.churchwebsite.churchwebsite.controller_advices;

import com.churchwebsite.churchwebsite.services.ContactUsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class DashboardControllerAdvise {

    private final ContactUsService contactUsService;

    public DashboardControllerAdvise(ContactUsService contactUsService) {
        this.contactUsService = contactUsService;
    }

    @ModelAttribute
    public void unreadMessages(HttpServletRequest request, Model model){
        String requestURI = request.getRequestURI();

        if(requestURI.startsWith("/dashboard")){
            model.addAttribute("unreadMessages", contactUsService.getUnreadContactUsMessages());
        }
    }
}
