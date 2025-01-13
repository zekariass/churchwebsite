package com.churchwebsite.churchwebsite.services.payment;

import com.churchwebsite.churchwebsite.entities.Donation;
import com.churchwebsite.churchwebsite.entities.DonationPurpose;
import com.churchwebsite.churchwebsite.repositories.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public Optional<Donation> getDonationById(Integer id) {
        return donationRepository.findById(id);
    }

    public Donation saveDonation(Donation donation) {
        return donationRepository.save(donation);
    }

    public void deleteDonation(Integer id) {
        donationRepository.deleteById(id);
    }

    public List<Donation> findAll() {
        return donationRepository.findAll();
    }

    public Page<Donation> findAll(int page, Integer pageSize, String sortBy) {
        Pageable pageable = getPageable(page, pageSize, sortBy);
        return donationRepository.findAll(pageable);
    }

    private Pageable getPageable(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "donationTime";
        }

        Pageable pageable;
        if(sortBy.equals("donationTime")){
            pageable = PageRequest.of(page -1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page -1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return pageable;
    }

    public Page<Donation> findByDonationPurpose(DonationPurpose donationPurpose, int page, Integer pageSize, String sortBy) {
        Pageable pageable = getPageable(page, pageSize, sortBy);
        return donationRepository.findByDonationPurpose(donationPurpose, pageable);
    }
}