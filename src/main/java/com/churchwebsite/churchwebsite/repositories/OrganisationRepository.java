package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganisationRepository extends JpaRepository<Organisation, Integer> {
}
