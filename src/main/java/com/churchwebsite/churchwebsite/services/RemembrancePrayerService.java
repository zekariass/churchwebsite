package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.repositories.RemembrancePrayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemembrancePrayerService {
    private final RemembrancePrayerRepository remembrancePrayerRepository;

    @Autowired
    public RemembrancePrayerService(RemembrancePrayerRepository remembrancePrayerRepository) {
        this.remembrancePrayerRepository = remembrancePrayerRepository;
    }
}
