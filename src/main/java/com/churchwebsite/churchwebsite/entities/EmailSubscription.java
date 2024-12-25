package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "email_subscription")
public class EmailSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subscriptionId;

    @Email
    private String email;

    public EmailSubscription() {}

    public EmailSubscription(String email) {
        this.email = email;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "EmailSubscription{" +
                "subscriptionId=" + subscriptionId +
                ", email='" + email + '\'' +
                '}';
    }
}
