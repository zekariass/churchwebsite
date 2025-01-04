package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.EmailSubscription;
import com.churchwebsite.churchwebsite.services.EmailSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("subscription")
public class EmailSubscriptionController {

    private final EmailSubscriptionService subscriptionService;

    @Autowired
    public EmailSubscriptionController(EmailSubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/email/subscribe")
    public String subscribeWithEmail(@RequestParam("email") String email){
        EmailSubscription emailSubscription = new EmailSubscription();
        emailSubscription.setEmail(email);
        subscriptionService.saveSubscription(emailSubscription);

        return "redirect:/?subscribed";
    }
}
