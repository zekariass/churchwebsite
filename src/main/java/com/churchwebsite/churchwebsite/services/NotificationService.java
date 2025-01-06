package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.EmailSubscription;
import com.churchwebsite.churchwebsite.entities.Member;
import com.churchwebsite.churchwebsite.entities.Notification;
import com.churchwebsite.churchwebsite.repositories.NotificationRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final EmailService emailService;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, EmailService emailService) {
        this.notificationRepository = notificationRepository;
        this.emailService = emailService;
    }


    public Notification findById(int id) {
        return notificationRepository.findById(id).orElse(null);
    }

    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    public void deleteById(int id) {
        notificationRepository.deleteById(id);
    }


    public Page<Notification> findAll(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "notificationSentTime";
        }

        Pageable pageable;
        if(sortBy.equals("notificationSentTime")){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return notificationRepository.findAll(pageable);
    }

    @Async
    public void sendEmailNotification(Notification notification, List<Member> members, List<EmailSubscription> emailSubscriptions) throws MessagingException {
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

    @Async
    public void sendPasswordResetEmail(String email, String subject, String htmlContent) throws MessagingException{
        emailService.sendEmail(email, subject, htmlContent);
    }
}
