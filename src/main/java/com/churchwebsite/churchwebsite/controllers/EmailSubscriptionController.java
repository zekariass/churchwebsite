package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.services.EmailSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("subscription")
public class EmailSubscriptionController {

    private final EmailSubscriptionService subscriptionService;

    @Autowired
    public EmailSubscriptionController(EmailSubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
}
