package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.BlogCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogCategoryRepository extends JpaRepository<BlogCategory, Integer> {
    Page<BlogCategory> findAll(Pageable pageable);
}
