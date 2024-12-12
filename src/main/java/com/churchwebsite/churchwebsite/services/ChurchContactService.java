package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.repositories.ChurchContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChurchContactService {

    private final ChurchContactRepository churchContactRepository;

    @Autowired
    public ChurchContactService(ChurchContactRepository churchContactRepository) {
        this.churchContactRepository = churchContactRepository;
    }
}
