package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.LandingContent;
import com.churchwebsite.churchwebsite.repositories.LandingContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LandingContentService {
    private final LandingContentRepository landingContentRepository;


    @Autowired
    public LandingContentService(LandingContentRepository informationService) {
        this.landingContentRepository = informationService;
    }

    public LandingContent save(LandingContent landingContent) {

        return landingContentRepository.save(landingContent);
    }

    public Page<LandingContent> findAll(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "contentCreationTime";
        }

        Pageable pageable;
        if(sortBy == "contentCreationTime"){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return landingContentRepository.findAll(pageable);
    }

    public List<LandingContent> findAll(){
        return landingContentRepository.findAll();
    }


    public LandingContent findById(int infoId) {
        return landingContentRepository.findById(infoId).orElseThrow(
                () -> new RuntimeException("No LandingContent Found!")
        );
    }

    public void deleteById(int contentId) {
        landingContentRepository.deleteById(contentId);
    }
}
