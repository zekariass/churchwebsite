package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.HolyMatrimony;
import com.churchwebsite.churchwebsite.entities.RemembrancePrayer;
import com.churchwebsite.churchwebsite.repositories.RemembrancePrayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RemembrancePrayerService {
    private final RemembrancePrayerRepository remembrancePrayerRepository;

    @Autowired
    public RemembrancePrayerService(RemembrancePrayerRepository remembrancePrayerRepository) {
        this.remembrancePrayerRepository = remembrancePrayerRepository;
    }

    public RemembrancePrayer save(RemembrancePrayer remembrancePrayer) {
        return remembrancePrayerRepository.save(remembrancePrayer);
    }

    public Page<RemembrancePrayer> findAll(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "requestDate";
        }

        Pageable pageable;
        if(sortBy == "requestDate"){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return remembrancePrayerRepository.findAll(pageable);
    }

    public RemembrancePrayer findById(int requestId) {
        return remembrancePrayerRepository.findById(requestId).orElseThrow(
                ()-> new RuntimeException("No Remembrance Request Found!")
        );
    }
}
