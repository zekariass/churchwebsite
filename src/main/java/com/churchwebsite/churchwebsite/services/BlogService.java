package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Blog;
import com.churchwebsite.churchwebsite.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BlogService {

    private final BlogRepository blogRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Page<Blog> findAll(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page-1, pageSize);
        return blogRepository.findAll(pageable);
    }

    public Blog save(Blog blog) {
        return blogRepository.save(blog);
    }

    public Blog findById(Integer blogId) {
        return blogRepository.findById(blogId).orElseThrow(
                () -> new RuntimeException("No blog found with id "+blogId)
        );
    }

    public void deleteById(int blogId) {
        blogRepository.deleteById(blogId);
    }
}
