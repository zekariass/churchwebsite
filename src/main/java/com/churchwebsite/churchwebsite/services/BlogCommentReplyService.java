package com.churchwebsite.churchwebsite.services;

import com.churchwebsite.churchwebsite.repositories.BlogCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogCommentReplyService {

    private final BlogCommentRepository blogCommentRepository;

    @Autowired
    public BlogCommentReplyService(BlogCommentRepository blogCommentRepository) {
        this.blogCommentRepository = blogCommentRepository;
    }

}
