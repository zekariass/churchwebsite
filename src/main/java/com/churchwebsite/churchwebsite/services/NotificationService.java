package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.BlogCategory;
import com.churchwebsite.churchwebsite.entities.Notification;
import com.churchwebsite.churchwebsite.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }


    public Notification findById(int id) {
        return notificationRepository.findById(id).orElse(null);
    }

    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    public void deleteById(int id) {
        notificationRepository.deleteById(id);
    }


    public Page<Notification> findAll(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "notificationSentTime";
        }

        Pageable pageable;
        if(sortBy.equals("notificationSentTime")){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return notificationRepository.findAll(pageable);
    }
}
