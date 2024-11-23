package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.repositories.BlogCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogCommentService {

    private final BlogCommentRepository blogCommentRepository;


    @Autowired
    public BlogCommentService(BlogCommentRepository blogCommentRepository) {
        this.blogCommentRepository = blogCommentRepository;
    }
}
