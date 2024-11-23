package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.BlogCommentReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogCommentReplyRepository extends JpaRepository<BlogCommentReply, Integer> {
}
