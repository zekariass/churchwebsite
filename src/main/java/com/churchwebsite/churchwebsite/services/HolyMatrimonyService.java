package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Baptism;
import com.churchwebsite.churchwebsite.entities.HolyMatrimony;
import com.churchwebsite.churchwebsite.repositories.HolyMatrimonyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class HolyMatrimonyService {

    private final HolyMatrimonyRepository holyMatrimonyRepository;

    @Autowired
    public HolyMatrimonyService(HolyMatrimonyRepository holyMatrimonyRepository) {
        this.holyMatrimonyRepository = holyMatrimonyRepository;
    }

    public HolyMatrimony save(HolyMatrimony matrimony) {
        return holyMatrimonyRepository.save(matrimony);
    }

    public Page<HolyMatrimony> findAll(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "requestDate";
        }

        Pageable pageable;
        if(sortBy == "requestDate"){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return holyMatrimonyRepository.findAll(pageable);
    }

    public HolyMatrimony findById(int requestId) {
        return holyMatrimonyRepository.findById(requestId).orElseThrow(
                () -> new RuntimeException("No Holy Matrimony Request Found!")
        );
    }
}
