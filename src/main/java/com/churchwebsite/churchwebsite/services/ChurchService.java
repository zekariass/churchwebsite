package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Church;
import com.churchwebsite.churchwebsite.repositories.ChurchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChurchService {
    private final ChurchRepository churchRepository;

    @Autowired
    public ChurchService(ChurchRepository churchRepository) {
        this.churchRepository = churchRepository;
    }

    public Church save(Church church){
        return churchRepository.save(church);
    }

    public List<Church> findAll(){
        return churchRepository.findAll();
    }

    public Optional<Church> find(int churchId) {

        return churchRepository.findById(churchId);
    }
}
