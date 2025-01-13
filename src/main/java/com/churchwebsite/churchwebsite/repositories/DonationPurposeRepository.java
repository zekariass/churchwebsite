package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.DonationPurpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationPurposeRepository extends JpaRepository<DonationPurpose, Integer> {
}