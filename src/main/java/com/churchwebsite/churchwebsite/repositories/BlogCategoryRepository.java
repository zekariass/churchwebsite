package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.BlogCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogCategoryRepository extends JpaRepository<BlogCategory, Integer> {
    List<BlogCategory> findAll();
    Page<BlogCategory> findAll(Pageable pageable);
}
