package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.ChurchContact;
import com.churchwebsite.churchwebsite.repositories.ChurchContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChurchContactService {

    private final ChurchContactRepository churchContactRepository;

    @Autowired
    public ChurchContactService(ChurchContactRepository churchContactRepository) {
        this.churchContactRepository = churchContactRepository;
    }

    public List<ChurchContact> findAllContacts() {
        return churchContactRepository.findAll();
    }

    public ChurchContact findContactById(int id) {
        return churchContactRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contact not found with ID: " + id));
    }

    public ChurchContact saveContact(ChurchContact contact) {
        return churchContactRepository.save(contact);
    }

    public ChurchContact updateContact(int id, ChurchContact contact) {
        ChurchContact existingContact = findContactById(id);

        existingContact.setContactFullName(contact.getContactFullName());
        existingContact.setContactPhone(contact.getContactPhone());
        existingContact.setContactEmail(contact.getContactEmail());
        existingContact.setContactDescription(contact.getContactDescription());

        return churchContactRepository.save(existingContact);
    }

    public void deleteContact(int id) {
        ChurchContact contact = findContactById(id);
        churchContactRepository.delete(contact);
    }
}
