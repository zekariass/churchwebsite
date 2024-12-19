package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.ChurchServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChurchServicesRepository extends JpaRepository<ChurchServices, Integer> {
    @Override
    Page<ChurchServices> findAll(Pageable pageable);
}
