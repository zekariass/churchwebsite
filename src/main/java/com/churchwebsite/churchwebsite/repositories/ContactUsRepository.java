package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.ContactUs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactUsRepository extends JpaRepository<ContactUs, Integer> {
    List<ContactUs> findByRead(boolean readStatus);

    Page<ContactUs> findByRead(Boolean readStatus, Pageable pageable);
}
