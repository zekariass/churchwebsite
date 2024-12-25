package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.EmailSubscription;
import com.churchwebsite.churchwebsite.repositories.EmailSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailSubscriptionService {

    private final EmailSubscriptionRepository subscriptionRepository;

    @Autowired
    public EmailSubscriptionService(EmailSubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<EmailSubscription> findAll() {
        return subscriptionRepository.findAll();
    }
}
