package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
}
