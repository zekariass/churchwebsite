package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.repositories.HolyMatrimonyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HolyMatrimonyService {

    private final HolyMatrimonyRepository holyMatrimonyRepository;

    @Autowired
    public HolyMatrimonyService(HolyMatrimonyRepository holyMatrimonyRepository) {
        this.holyMatrimonyRepository = holyMatrimonyRepository;
    }
}
