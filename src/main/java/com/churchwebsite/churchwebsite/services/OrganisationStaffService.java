package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.repositories.OrganisationStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganisationStaffService {

    private final OrganisationStaffRepository organisationStaffRepository;

    @Autowired
    public OrganisationStaffService(OrganisationStaffRepository organisationStaffRepository) {
        this.organisationStaffRepository = organisationStaffRepository;
    }
}
