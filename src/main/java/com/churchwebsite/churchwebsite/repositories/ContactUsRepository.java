package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactUsRepository extends JpaRepository<ContactUs, Integer> {
}
