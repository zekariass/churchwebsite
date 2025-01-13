package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.entities.Blog;
import com.churchwebsite.churchwebsite.entities.BlogCategory;
import com.churchwebsite.churchwebsite.repositories.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final BlogCategoryService blogCategoryService;

    @Autowired
    public BlogService(BlogRepository blogRepository,
                       BlogCategoryService blogCategoryService) {
        this.blogRepository = blogRepository;
        this.blogCategoryService = blogCategoryService;
    }

    public Page<Blog> findAll(int page, int pageSize, int blogCategoryId) {
        Pageable pageable = PageRequest.of(page-1, pageSize);

        BlogCategory blogCategory = null;

        if(blogCategoryId != 0){
            blogCategory = blogCategoryService.findById(blogCategoryId);
        }

        if(blogCategory != null){
            return blogRepository.findByBlogCategory(pageable, blogCategory);
        }else{
            return blogRepository.findAll(pageable);
        }

    }

    public Page<Blog> findBlogsByArchive(int page, int pageSize, int blogCategoryId, boolean archived) {
        Pageable pageable = PageRequest.of(page-1, pageSize);

        BlogCategory blogCategory = null;

        if(blogCategoryId != 0){
            blogCategory = blogCategoryService.findById(blogCategoryId);
        }

        if(blogCategory != null){
            return blogRepository.findByBlogCategoryAndArchived(pageable, blogCategory, archived);
        }else{
            return blogRepository.findAllByArchived(archived, pageable);
        }

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

    public Page<Blog> findBlogsByArchiveAndActive(int page, Integer pageSize, Integer blogCategoryId, boolean archived, boolean active) {
        Pageable pageable = PageRequest.of(page-1, pageSize);

        BlogCategory blogCategory = null;

        if(blogCategoryId != 0){
            blogCategory = blogCategoryService.findById(blogCategoryId);
        }

        if(blogCategory != null){
            return blogRepository.findByBlogCategoryAndArchivedAndActive(pageable, blogCategory, archived, active);
        }else{
            return blogRepository.findAllByArchivedAndActive(archived, pageable, active);
        }
    }

}
