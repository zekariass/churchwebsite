package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Blog;
import com.churchwebsite.churchwebsite.entities.BlogCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    Page<Blog> findAll(Pageable pageable);
    Page<Blog> findByBlogCategory(Pageable pageable, BlogCategory blogCategory);
    Page<Blog> findAllByArchived(Boolean archived, Pageable pageable);
    Page<Blog> findByBlogCategoryAndArchived(Pageable pageable, BlogCategory blogCategory, Boolean archived);

    Page<Blog> findByBlogCategoryAndArchivedAndActive(Pageable pageable, BlogCategory blogCategory, boolean archived, boolean active);

    Page<Blog> findAllByArchivedAndActive(boolean archived, Pageable pageable, boolean active);
}
