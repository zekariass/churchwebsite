package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.ContactUs;
import com.churchwebsite.churchwebsite.entities.MessageReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageReplyRepository extends JpaRepository<MessageReply, Integer> {
    List<MessageReply> findByContactUsMessage(ContactUs contactUs);
}
