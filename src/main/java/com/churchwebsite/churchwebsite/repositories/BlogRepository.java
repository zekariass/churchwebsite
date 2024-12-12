package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Blog;
import com.churchwebsite.churchwebsite.entities.BlogCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    Page<Blog> findAll(Pageable pageable);
    Page<Blog> findByBlogCategory(Pageable pageable, BlogCategory blogCategory);
}
