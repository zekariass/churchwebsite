package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.ChurchServices;
import com.churchwebsite.churchwebsite.repositories.ChurchServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ChurchServicesService {
    private final ChurchServicesRepository churchServicesRepository;

    @Autowired
    public ChurchServicesService(ChurchServicesRepository churchServicesRepository) {
        this.churchServicesRepository = churchServicesRepository;
    }

    public ChurchServices save(ChurchServices service) {
        return churchServicesRepository.save(service);
    }


    public Page<ChurchServices> findAll(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "serviceName";
        }

        Pageable pageable;
        if(sortBy == "serviceName"){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return churchServicesRepository.findAll(pageable);
    }

    public ChurchServices findById(int serviceId) {
        return churchServicesRepository.findById(serviceId).orElseThrow(
                () -> new RuntimeException("No ChurchService Found!")
        );
    }

    public void deleteById(int serviceId) {
        churchServicesRepository.deleteById(serviceId);
    }
}
