package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.ContactUs;
import com.churchwebsite.churchwebsite.services.*;
import com.churchwebsite.churchwebsite.utils.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final ChurchDetailService churchDetailService;
    private final UserService userService;
    private final ContactUsService contactUsService;
    private final PaginationService paginationService;

//    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";
    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-layouts/dash-base";

    @Autowired
    public DashboardController(ChurchDetailService churchDetailService, UserService userService, ContactUsService contactUsService, PaginationService paginationService) {
        this.churchDetailService = churchDetailService;
        this.userService = userService;
        this.contactUsService = contactUsService;
        this.paginationService = paginationService;
    }

    @GetMapping("/home")
    public String dashboard(Model model){

        CustomUserDetails userDetail = userService.getCurrentUser();
        model.addAttribute("activeDashPage", "dash-landing");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("userProfile", userDetail.getUser().getUserProfile());
        model.addAttribute("user", userDetail.getUser());

        model.addAttribute("unreadMessages", contactUsService.getUnreadContactUsMessages());
        model.addAttribute("pageTitle", "Dashboard");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/messages")
    public String messages(

            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "membershipDate") String sortBy,
            @RequestParam(value = "readStatus", required = false) Boolean readStatus,
            HttpServletRequest request,
            Model model){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();
        Page<ContactUs> contactUsMessages;

        if(readStatus != null){
            contactUsMessages = contactUsService.findUnreadMessages(readStatus, page, pageSize, sortBy);
        }else{
            contactUsMessages = contactUsService.findAll(page, pageSize, sortBy);
        }


        List<ContactUs> messages = contactUsMessages.getContent();

        model.addAttribute("activeDashPage", "contact-us-messages-list");
        model.addAttribute("messages", messages);
        model.addAttribute("unreadMessages", contactUsService.getUnreadContactUsMessages());

        model.addAttribute("currentPage", contactUsMessages.getNumber()+1);
        model.addAttribute("totalItems", contactUsMessages.getTotalElements());
        model.addAttribute("totalPages", contactUsMessages.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Contact Us Messages List");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/messages/detail/{id}")
    public String viewMessages(@PathVariable("id") int id, Model model){

        ContactUs contactUs = contactUsService.findById(id);

        if(contactUs != null && contactUs.getContactUsId() != 0 && !contactUs.getRead()){
            contactUs.setRead(true);
            contactUs.setReadTime(LocalDateTime.now());
            contactUsService.save(contactUs);
        }

        model.addAttribute("activeDashPage", "contact-us-message-detail");
        model.addAttribute("message", contactUs);
        model.addAttribute("unreadMessages", contactUsService.getUnreadContactUsMessages());

        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("unreadMessages", contactUsService.getUnreadContactUsMessages());
        model.addAttribute("pageTitle", "Contact Us Message Detail");


        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/messages/delete/{id}")
    public String deleteMessage(@PathVariable("id") int id, Model model){

        contactUsService.deleteById(id);

        return "redirect:/dashboard/messages";
    }

    @GetMapping("/events")
    public String events(Model model){
        model.addAttribute("activeDashPage", "events");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("unreadMessages", contactUsService.getUnreadContactUsMessages());
        model.addAttribute("pageTitle", "Events List");

        return DASHBOARD_MAIN_PANEL;
    }


}
