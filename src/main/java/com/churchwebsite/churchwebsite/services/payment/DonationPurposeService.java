package com.churchwebsite.churchwebsite.services.payment;

import com.churchwebsite.churchwebsite.entities.DonationPurpose;
import com.churchwebsite.churchwebsite.repositories.DonationPurposeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationPurposeService {

    @Autowired
    private DonationPurposeRepository donationPurposeRepository;

    public List<DonationPurpose> getAllDonationPurposes() {
        return donationPurposeRepository.findAll();
    }

    public Optional<DonationPurpose> getDonationPurposeById(Integer id) {
        return donationPurposeRepository.findById(id);
    }

    public DonationPurpose saveDonationPurpose(DonationPurpose donationPurpose) {
        return donationPurposeRepository.save(donationPurpose);
    }

    public void deleteDonationPurpose(Integer id) {
        donationPurposeRepository.deleteById(id);
    }
}
