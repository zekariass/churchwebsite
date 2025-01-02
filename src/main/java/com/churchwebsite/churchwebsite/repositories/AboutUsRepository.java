package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.AboutUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutUsRepository extends JpaRepository<AboutUs, Integer> {
}
