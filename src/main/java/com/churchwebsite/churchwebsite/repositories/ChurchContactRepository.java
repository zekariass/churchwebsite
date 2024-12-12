package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.ChurchContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChurchContactRepository extends JpaRepository<ChurchContact, Integer> {
}
