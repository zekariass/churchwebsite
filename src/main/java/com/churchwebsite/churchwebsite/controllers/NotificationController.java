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

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/dashboard/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final MemberService memberService;
    private final EmailService emailService;
    private final PaginationService paginationService;
    private final EmailSubscriptionService emailSubscriptionService;

    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public NotificationController(NotificationService notificationService,
                                  MemberService memberService,
                                  EmailService emailService,
                                  PaginationService paginationService,
                                  EmailSubscriptionService emailSubscriptionService) {
        this.notificationService = notificationService;
        this.memberService = memberService;
        this.emailService = emailService;
        this.paginationService = paginationService;
        this.emailSubscriptionService = emailSubscriptionService;
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

        return DASHBOARD_MAIN_PANEL;
    }

    // Create a new notification
    @GetMapping("/form")
    public String showNotificationForm(Model model) {

        model.addAttribute("notification", new Notification());
        model.addAttribute("activeDashPage", "notification-form");

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/form/process")
    public String processNotificationForm(@ModelAttribute Notification notification,
                                          Model model) {
        // Send notification emails to all registered members
        List<Member> members = memberService.findAll();
        List<EmailSubscription> emailSubscriptions = emailSubscriptionService.findAll();

        try {
            sendEmailNotification(notification, members, emailSubscriptions);
        }catch (MessagingException me){
            model.addAttribute("activeDashPage", "notification-form");
            model.addAttribute("error", me.getMessage());
            return DASHBOARD_MAIN_PANEL;
        }

        // Save the notification
        notificationService.save(notification);

        return "redirect:/dashboard/notifications";
    }

    private void sendEmailNotification(Notification notification, List<Member> members, List<EmailSubscription> emailSubscriptions) throws MessagingException {
        Set<String> emails = new LinkedHashSet<>();

        // Get all emails from registered members
        for(Member member: members){
            emails.add(member.getEmail());
        }

        // Get all emails in the email_subscription table
        for(EmailSubscription subscription: emailSubscriptions){
            emails.add(subscription.getEmail());
        }

        // Send email notifications
        for (String email : emails) {
            emailService.sendEmail(
                    email,
                    notification.getNotificationSubject(),
                    notification.getNotificationMessage()
            );
        }
    }

    // View details of a notification
    @GetMapping("/detail/{id}")
    public String showNotificationDetail(@PathVariable("id") int id, Model model) {

        Notification notification = notificationService.findById(id);
        model.addAttribute("notification", notification);
        model.addAttribute("activeDashPage", "notification-detail");

        return DASHBOARD_MAIN_PANEL;
    }


    // Resend a notification
    @PostMapping("/{id}/resend")
    public String resendNotification(@PathVariable("id") int id, Model model) {
        Notification notification = notificationService.findById(id);
        List<Member> members = memberService.findAll();
        List<EmailSubscription> emailSubscriptions = emailSubscriptionService.findAll();

        try {
            sendEmailNotification(notification, members, emailSubscriptions);
        }catch (MessagingException me){
            model.addAttribute("activeDashPage", "notification-form");
            model.addAttribute("error", me.getMessage());
            return DASHBOARD_MAIN_PANEL;
        }

        return "redirect:/dashboard/notifications";
    }


//    // Edit a notification
//    @GetMapping("/edit/{id}")
//    public String editNotificationForm(@PathVariable("id") int id, Model model) {
//        Notification notification = notificationService.findById(id);
//        model.addAttribute("notification", notification);
//        return "notifications/form";
//    }

//    @PostMapping("/{id}")
//    public String updateNotification(@PathVariable("id") int id, @ModelAttribute Notification notification) {
//        notification.setNotificationId(id);
//        notificationService.save(notification);
//        return "redirect:/dashboard/notifications";
//    }

    // Delete a notification
    @PostMapping("/delete/{id}")
    public String deleteNotification(@PathVariable("id") int id) {
        notificationService.deleteById(id);
        return "redirect:/dashboard/notifications";
    }
}
