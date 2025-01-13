package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Donation;
import com.churchwebsite.churchwebsite.entities.DonationPurpose;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Integer> {
    Page<Donation> findByDonationPurpose(DonationPurpose donationPurpose, Pageable pageable);
}
