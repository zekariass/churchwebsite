package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    Page<Announcement> findByArchivedAndActiveAndFeatured(Boolean archived, Boolean active, Boolean featured, Pageable pageable);

    Page<Announcement> findByArchivedAndActive(Boolean archived, Boolean active, Pageable pageable);

    List<Announcement> findByArchivedAndActiveAndFeatured(boolean archived, boolean active, boolean featured);
}
