package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Donation;
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

    public void sendContactUsReplyEmail( String receiverEmail, String subject, String message, boolean followUp) throws MessagingException {
        String emailSubject = subject;
        if(followUp){
            emailSubject = emailSubject + " - This is a follow up email";
        }

        emailService.sendEmail(receiverEmail, emailSubject, message);
    }

    @Async
    public void sendDonationSuccessEmail(Donation donation, String churchName) throws MessagingException {
        if (donation != null && donation.getDonorEmail() != null && !donation.getDonorEmail().isEmpty()) {
            StringBuilder emailText = new StringBuilder();
            emailText.append("<!DOCTYPE html>")
                    .append("<html lang='en'>")
                    .append("<head>")
                    .append("<meta charset='UTF-8'>")
                    .append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>")
                    .append("<title>Donation Success</title>")
                    .append("<style>")
                    .append("body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; }")
                    .append(".email-container { max-width: 600px; margin: auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }")
                    .append(".header { text-align: center; color: #007BFF; margin-bottom: 20px; }")
                    .append(".content { line-height: 1.6; }")
                    .append(".footer { font-size: 12px; color: #555555; margin-top: 20px; }")
                    .append("</style>")
                    .append("</head>")
                    .append("<body>")
                    .append("<div class='email-container'>")
                    .append("<div class='header'>")
                    .append("<h1>Thank You for Your Donation!</h1>")
                    .append("</div>")
                    .append("<div class='content'>")
                    .append("<p>Dear <strong>").append(donation.getDonorFullName()).append("</strong>,</p>")
                    .append("<p>Your donation of amount <strong>").append(donation.getDonationAmount()).append("</strong> has been received.</p>")
                    .append("<p>God bless you!</p>")
                    .append("</div>")
                    .append("<div class='footer'>")
                    .append("<p>").append(churchName).append("</p>")
                    .append("</div>")
                    .append("</div>")
                    .append("</body>")
                    .append("</html>");

            emailService.sendEmail(donation.getDonorEmail(), "Donation Success", emailText.toString());
        }

    }

    public void sendDonationFailureEmail(Donation donation, String churchName) throws MessagingException {
        if (donation != null && donation.getDonorEmail() != null && !donation.getDonorEmail().isEmpty()) {
            StringBuilder emailText = new StringBuilder();
            emailText.append("<!DOCTYPE html>")
                    .append("<html lang='en'>")
                    .append("<head>")
                    .append("<meta charset='UTF-8'>")
                    .append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>")
                    .append("<title>Donation Failed</title>")
                    .append("<style>")
                    .append("body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; }")
                    .append(".email-container { max-width: 600px; margin: auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }")
                    .append(".header { text-align: center; color: #DC3545; margin-bottom: 20px; }")
                    .append(".content { line-height: 1.6; }")
                    .append(".footer { font-size: 12px; color: #555555; margin-top: 20px; }")
                    .append("</style>")
                    .append("</head>")
                    .append("<body>")
                    .append("<div class='email-container'>")
                    .append("<div class='header'>")
                    .append("<h1>Donation Failed</h1>")
                    .append("</div>")
                    .append("<div class='content'>")
                    .append("<p>Dear <strong>").append(donation.getDonorFullName()).append("</strong>,</p>")
                    .append("<p>We regret to inform you that your donation attempt of amount <strong>").append(donation.getDonationAmount()).append("</strong> was unsuccessful.</p>")
                    .append("<p>Please try again, or contact our support team if the issue persists.</p>")
                    .append("<p>We appreciate your willingness to support us, and we are here to assist you if needed.</p>")
                    .append("</div>")
                    .append("<div class='footer'>")
                    .append("<p>").append(churchName).append("</p>")
                    .append("</div>")
                    .append("</div>")
                    .append("</body>")
                    .append("</html>");

            emailService.sendEmail(donation.getDonorEmail(), "Donation Failed", emailText.toString());
        }

    }
}
