package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.EmailSubscription;
import com.churchwebsite.churchwebsite.entities.Member;
import com.churchwebsite.churchwebsite.entities.Notification;
import com.churchwebsite.churchwebsite.services.*;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final MemberService memberService;
    private final EmailService emailService;
    private final PaginationService paginationService;
    private final EmailSubscriptionService emailSubscriptionService;
    private final ChurchDetailService churchDetailService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public NotificationController(NotificationService notificationService,
                                  MemberService memberService,
                                  EmailService emailService,
                                  PaginationService paginationService,
                                  EmailSubscriptionService emailSubscriptionService, ChurchDetailService churchDetailService) {
        this.notificationService = notificationService;
        this.memberService = memberService;
        this.emailService = emailService;
        this.paginationService = paginationService;
        this.emailSubscriptionService = emailSubscriptionService;
        this.churchDetailService = churchDetailService;
    }

    // List all notifications
    @GetMapping("")
    public String listEmailNotifications(Model model,
                                         @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                         @RequestParam(value = "size", required = false) Integer pageSize,
                                         @RequestParam(value = "sortBy", defaultValue = "notificationSentTime", required = false) String sortBy,
                                         HttpServletRequest request){

        pageSize = (pageSize != null && pageSize > 0) ? pageSize: paginationService.getPageSize();

        Page<Notification> pagedNotifications = notificationService.findAll(page, pageSize, sortBy);
        List<Notification> notifications = pagedNotifications.getContent();

        model.addAttribute("notifications", notifications);
        model.addAttribute("activeDashPage", "notifications-list");
        model.addAttribute("currentPage", pagedNotifications.getNumber()+1);
        model.addAttribute("totalItems", pagedNotifications.getTotalElements());
        model.addAttribute("totalPages", pagedNotifications.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentUrl", request.getRequestURL());
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Notifications List");

        return DASHBOARD_MAIN_PANEL;
    }

    // Create a new notification
    @GetMapping("/form")
    public String showNotificationForm(Model model) {

        model.addAttribute("notification", new Notification());
        model.addAttribute("activeDashPage", "notification-form");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Notifications Form");

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/form/process")
    public String processNotificationForm(@ModelAttribute Notification notification,
                                          Model model) {
        // Send notification emails to all registered members
        List<Member> members = memberService.findAll();
        List<EmailSubscription> emailSubscriptions = emailSubscriptionService.findAll();

        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());


        try {
            notificationService.sendEmailNotification(notification, members, emailSubscriptions);
        }catch (MessagingException me){
            model.addAttribute("activeDashPage", "notification-form");
            model.addAttribute("error", me.getMessage());
            return DASHBOARD_MAIN_PANEL;
        }

        // Save the notification
        notificationService.save(notification);

        return "redirect:/dashboard/notifications?resent";
    }

    // View details of a notification
    @GetMapping("/detail/{id}")
    public String showNotificationDetail(@PathVariable("id") int id, Model model) {

        Notification notification = notificationService.findById(id);
        model.addAttribute("notification", notification);
        model.addAttribute("activeDashPage", "notification-detail");
        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());
        model.addAttribute("pageTitle", "Notification Detail");

        return DASHBOARD_MAIN_PANEL;
    }


    // Resend a notification
    @PostMapping("/{id}/resend")
    public String resendNotification(@PathVariable("id") int id, Model model) {

        model.addAttribute("churchDetail", churchDetailService.getChurchDetail());

        Notification notification = notificationService.findById(id);
        List<Member> members = memberService.findAll();
        List<EmailSubscription> emailSubscriptions = emailSubscriptionService.findAll();

        try {
            notificationService.sendEmailNotification(notification, members, emailSubscriptions);
        }catch (MessagingException me){
            model.addAttribute("activeDashPage", "notification-form");
            model.addAttribute("error", me.getMessage());
            model.addAttribute("pageTitle", "Notifications List");

            return DASHBOARD_MAIN_PANEL;
        }

        return "redirect:/dashboard/notifications?resent";
    }

    // Delete a notification
    @PostMapping("/{id}/delete")
    public String deleteNotification(@PathVariable("id") int id) {
        notificationService.deleteById(id);
        return "redirect:/dashboard/notifications";
    }
}
