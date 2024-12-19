package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.repositories.BaptismRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaptismService {

    private final BaptismRepository baptismRepository;

    @Autowired
    public BaptismService(BaptismRepository baptismRepository) {
        this.baptismRepository = baptismRepository;
    }
}
