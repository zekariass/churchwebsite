package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Integer> {
    Page<Video> findAll(Pageable pageable);

    Page<Video> findByArchived(Boolean archived, Pageable pageable);
}
