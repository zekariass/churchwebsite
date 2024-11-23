package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Integer> {
}
