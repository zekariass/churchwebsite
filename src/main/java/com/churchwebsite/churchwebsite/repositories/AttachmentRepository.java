package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Attachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
    Page<Attachment> findAll(Pageable pageable);
}
