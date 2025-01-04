package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.BlogCategory;
import com.churchwebsite.churchwebsite.repositories.BlogCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogCategoryService {

    private final BlogCategoryRepository blogCategoryRepository;


    @Autowired
    public BlogCategoryService(BlogCategoryRepository blogCategoryRepository) {
        this.blogCategoryRepository = blogCategoryRepository;
    }

    public BlogCategory save(BlogCategory blogCategory) {
        return blogCategoryRepository.save(blogCategory);
    }

    public Page<BlogCategory> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        return blogCategoryRepository.findAll(pageable);
    }

    public List<BlogCategory> findAll() {
        List<BlogCategory> blogCategories = blogCategoryRepository.findAll();
        return blogCategories;
    }

    public BlogCategory findById(Integer catId) {

        return blogCategoryRepository.findById(catId).orElseThrow(
                ()-> new RuntimeException("No Blog Category Found!")
                );
    }

    public void deleteById(int blogCatId) {
        blogCategoryRepository.deleteById(blogCatId);
    }

}
