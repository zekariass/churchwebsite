package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.ContactUs;
import com.churchwebsite.churchwebsite.entities.MessageReply;
import com.churchwebsite.churchwebsite.repositories.MessageReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageReplyService {

    private final MessageReplyRepository messageReplyRepository;

    @Autowired
    public MessageReplyService(MessageReplyRepository messageReplyRepository) {
        this.messageReplyRepository = messageReplyRepository;
    }

    public List<MessageReply> findAll() {
        return messageReplyRepository.findAll();
    }

    public Optional<MessageReply> findById(Integer id) {
        return messageReplyRepository.findById(id);
    }

    public MessageReply save(MessageReply messageReplyEmail) {
        return messageReplyRepository.save(messageReplyEmail);
    }

    public void deleteById(Integer id) {
        messageReplyRepository.deleteById(id);
    }


    public List<MessageReply> findByContactUsMessage(ContactUs contactUs) {
        return messageReplyRepository.findByContactUsMessage(contactUs);
    }
}
