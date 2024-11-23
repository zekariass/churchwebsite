package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.repositories.OrganisationContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganisationContactService {

    private final OrganisationContactRepository organisationContactRepository;

    @Autowired
    public OrganisationContactService(OrganisationContactRepository organisationContactRepository) {
        this.organisationContactRepository = organisationContactRepository;
    }
}
