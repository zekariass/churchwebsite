package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.EmailSubscription;
import com.churchwebsite.churchwebsite.repositories.EmailSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void saveSubscription(EmailSubscription emailSubscription) {
        Optional<EmailSubscription> subscription = Optional.ofNullable(subscriptionRepository.findByEmail(emailSubscription.getEmail()));
        if(subscription.isEmpty()){
            subscriptionRepository.save(emailSubscription);
        }
    }
}
