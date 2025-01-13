package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {
    Page<News> findByArchivedAndActiveAndFeatured(Boolean archived, Boolean active, Boolean featured, Pageable pageable);

    Page<News> findByArchivedAndActive(Boolean archived, Boolean active, Pageable pageable);

    List<News> findByArchivedAndActiveAndFeatured(boolean archived, boolean active, boolean featured);
}
