package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogCommentRepository extends JpaRepository<BlogComment, Integer> {
}
