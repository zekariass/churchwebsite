package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Announcement;
import com.churchwebsite.churchwebsite.repositories.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    public List<Announcement> getAllAnnouncements() {
        return announcementRepository.findAll();
    }

    public Optional<Announcement> getAnnouncementById(Integer id) {
        return announcementRepository.findById(id);
    }

    public Announcement saveAnnouncement(Announcement announcement) {
        return announcementRepository.save(announcement);
    }

    public void deleteAnnouncement(Integer id) {
        announcementRepository.deleteById(id);
    }

    public Page<Announcement> getAllShipments(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "createdAt";
        }
        Pageable pageable;
        if(sortBy.equals("createdAt") || sortBy.equals("lastModifiedAt")){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return announcementRepository.findAll(pageable);
    }
}
