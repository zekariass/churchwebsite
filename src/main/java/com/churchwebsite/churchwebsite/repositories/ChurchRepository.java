package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Church;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChurchRepository extends JpaRepository<Church, Integer> {
}
