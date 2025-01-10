package com.churchwebsite.churchwebsite.repositories;

import com.churchwebsite.churchwebsite.entities.Attachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
    Page<Attachment> findAll(Pageable pageable);

    Page<Attachment> findByArchived(Boolean archived, Pageable pageable);
}
