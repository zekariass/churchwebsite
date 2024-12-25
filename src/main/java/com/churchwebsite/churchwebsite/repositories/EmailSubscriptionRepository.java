package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.EmailSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailSubscriptionRepository extends JpaRepository<EmailSubscription, Integer> {
}
