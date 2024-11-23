package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.dtos.OrganisationDetailDTO;
import com.churchwebsite.churchwebsite.entities.Organisation;
import com.churchwebsite.churchwebsite.repositories.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganisationService {
    private final OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    public Organisation save(Organisation organisation){
        return organisationRepository.save(organisation);
    }

    public List<Organisation> findAll(){
        return organisationRepository.findAll();
    }

    public Optional<Organisation> find(int organisationId) {

        return organisationRepository.findById(organisationId);
    }
}
