package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {
    Page<News> findByArchivedAndActiveAndFeatured(Boolean archived, Boolean active, Boolean featured, Pageable pageable);

    Page<News> findByArchivedAndActive(Boolean archived, Boolean active, Pageable pageable);
}
