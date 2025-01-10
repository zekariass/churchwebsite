package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.ContactUs;
import com.churchwebsite.churchwebsite.repositories.ContactUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactUsService {

    private final ContactUsRepository contactUsRepository;

    @Autowired
    public ContactUsService(ContactUsRepository contactUsRepository) {
        this.contactUsRepository = contactUsRepository;
    }

    public void save(ContactUs contactUs) {
        contactUsRepository.save(contactUs);
    }

    public Page<ContactUs> findAll(int page, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(page-1, pageSize, Sort.by(
                Sort.Order.asc("read"),
                Sort.Order.desc("messageTime")
        ));

        return contactUsRepository.findAll(pageable);
    }

    public ContactUs findById(int id) {
        return contactUsRepository.findById(id).orElse(new ContactUs());
    }

    public void deleteById(int id) {
        contactUsRepository.deleteById(id);
    }

    public List<ContactUs> getUnreadContactUsMessages() {
        return contactUsRepository.findByRead(false);
    }

    public Page<ContactUs> findUnreadMessages(Boolean readStatus, int page, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(page-1, pageSize, Sort.by(
                Sort.Order.desc("messageTime")
        ));

        return contactUsRepository.findByRead(readStatus, pageable);
    }
}
