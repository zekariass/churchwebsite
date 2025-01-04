package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.AboutUs;
import com.churchwebsite.churchwebsite.repositories.AboutUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AboutUsService {

    @Autowired
    private AboutUsRepository aboutUsRepository;

    public Optional<AboutUs> getAboutUs() {
        return aboutUsRepository.findById(1); // Assuming a single record for "About Us"
    }

    public AboutUs saveAboutUs(AboutUs aboutUs) {
        return aboutUsRepository.save(aboutUs);
    }

    public Page<AboutUs> getAllAboutUs(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "lastModifiedUs";
        }
        Pageable pageable;
        if(sortBy.equals("createdAt") || sortBy.equals("lastModifiedAt")){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return aboutUsRepository.findAll(pageable);
    }

    public AboutUs getAboutUsById(Integer id) {
        return aboutUsRepository.findById(id).orElse(new AboutUs());
    }

    public void deleteAboutUs(Integer id) {
        aboutUsRepository.deleteById(id);
    }

    public List<AboutUs> getAllAboutUs() {

        return aboutUsRepository.findAll();
    }
}
