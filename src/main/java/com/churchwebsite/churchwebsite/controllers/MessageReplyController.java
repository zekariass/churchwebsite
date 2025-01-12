package com.churchwebsite.churchwebsite.controllers;

import com.churchwebsite.churchwebsite.entities.ContactUs;
import com.churchwebsite.churchwebsite.entities.MessageReply;
import com.churchwebsite.churchwebsite.services.ContactUsService;
import com.churchwebsite.churchwebsite.services.MessageReplyService;
import com.churchwebsite.churchwebsite.services.NotificationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dashboard/messages/replies")
public class MessageReplyController {

    private final MessageReplyService messageReplyService;
    private final ContactUsService contactUsService;
    private final NotificationService notificationService;
    private final String DASHBOARD_MAIN_PANEL = "dashboard/dash-fragments/dash-main-panel";

    @Autowired
    public MessageReplyController(MessageReplyService messageReplyEmailService, ContactUsService contactUsService, NotificationService notificationService) {
        this.messageReplyService = messageReplyEmailService;
        this.contactUsService = contactUsService;
        this.notificationService = notificationService;
    }

    @GetMapping("/{contactUsMessageId}")
    public String listMessageReplies(@PathVariable("contactUsMessageId") int id, Model model) {
        ContactUs contactUs = contactUsService.findById(id);
        List<MessageReply> messageReplies = messageReplyService.findByContactUsMessage(contactUs);

        model.addAttribute("replies", messageReplies);
        model.addAttribute("pageTitle", "Message Replies List");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/detail/{replyId}")
    public String showReplyMessageDetail(@PathVariable("replyId") int id, Model model) {
        MessageReply messageReply = messageReplyService.findById(id).orElse(null);
        model.addAttribute("activeDashPage", "contact-us-message-reply-detail");

        model.addAttribute("reply", messageReply);
        model.addAttribute("pageTitle", "Message Reply Detail");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/form/{contactUsId}")
    public String viewMessage(@PathVariable("contactUsId") Integer contactUsId,
                              Model model) {

        ContactUs contactUs = contactUsService.findById(contactUsId);

        model.addAttribute("contactUs", contactUs);
        model.addAttribute("messageReply", new MessageReply());
        model.addAttribute("activeDashPage", "contact-us-message-reply-form");
        model.addAttribute("pageTitle", "Message Reply Form");

        return DASHBOARD_MAIN_PANEL;
    }

    @GetMapping("/edit-and-resend/{replyId}")
    public String editAndResendMessage(@PathVariable("replyId") Integer replyId,
                              Model model) {

        MessageReply reply = messageReplyService.findById(replyId).orElse(new MessageReply());

        model.addAttribute("contactUs", reply.getContactUsMessage());
        model.addAttribute("messageReply", reply);
        model.addAttribute("activeDashPage", "contact-us-message-reply-form");
        model.addAttribute("pageTitle", "Message Reply Form");

        return DASHBOARD_MAIN_PANEL;
    }

    @PostMapping("/form/{contactUsId}")
    public String saveMessage(@Valid @ModelAttribute MessageReply reply,
                              BindingResult result,
                              @PathVariable("contactUsId") int contactUsId,
                              Model model) {

        ContactUs contactUs = contactUsService.findById(contactUsId);

        if(result.hasErrors()){
            model.addAttribute("contactUs", contactUs);
            model.addAttribute("messageReply", reply);
            model.addAttribute("activeDashPage", "contact-us-message-reply-form");
            model.addAttribute("pageTitle", "Message Reply Form");

            return DASHBOARD_MAIN_PANEL;
        }
        reply.setContactUsMessage(contactUs);

        try {
            notificationService.sendContactUsReplyEmail(reply.getReceiverEmail(), reply.getSubject(), reply.getMessage(), reply.isFollowUp());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        messageReplyService.save(reply);
        return "redirect:/dashboard/messages/detail/"+contactUsId;
    }

    @GetMapping("/delete/{id}")
    public String deleteMessage(@PathVariable Integer id) {
        MessageReply messageReply = messageReplyService.findById(id).orElse(null);
        assert messageReply != null;
        ContactUs contactUs = messageReply.getContactUsMessage();
        messageReplyService.deleteById(id);
        return "redirect:/dashboard/messages/detail/"+contactUs.getContactUsId();
    }
}

