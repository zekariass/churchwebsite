package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.repositories.ChurchStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChurchStaffService {

    private final ChurchStaffRepository churchStaffRepository;

    @Autowired
    public ChurchStaffService(ChurchStaffRepository churchStaffRepository) {
        this.churchStaffRepository = churchStaffRepository;
    }
}
