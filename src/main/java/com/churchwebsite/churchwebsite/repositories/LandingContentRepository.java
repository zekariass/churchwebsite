package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.LandingContent;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LandingContentRepository extends JpaRepository<LandingContent, Integer> {
    List<LandingContent> findByActiveAndArchived(Sort sort, boolean active, boolean archived);
}
