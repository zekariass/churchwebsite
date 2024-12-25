package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Baptism;
import com.churchwebsite.churchwebsite.repositories.BaptismRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class BaptismService {

    private final BaptismRepository baptismRepository;

    @Autowired
    public BaptismService(BaptismRepository baptismRepository) {
        this.baptismRepository = baptismRepository;
    }

    public Baptism save(Baptism baptism) {
        return baptismRepository.save(baptism);
    }

    public Page<Baptism> findAll(int page, Integer pageSize, String sortBy) {
        if(sortBy.isEmpty()){
            sortBy = "requestDate";
        }

        Pageable pageable;
        if(sortBy == "requestDate"){
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.desc(sortBy)));
        }else{
            pageable = PageRequest.of(page-1, pageSize, Sort.by(Sort.Order.asc(sortBy)));
        }
        return baptismRepository.findAll(pageable);
    }

    public Baptism findById(int requestId) {
        return baptismRepository.findById(requestId).orElseThrow(
                ()-> new RuntimeException("No Baptism Request Found!")
        );
    }
}
