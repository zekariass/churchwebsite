package com.churchwebsite.churchwebsite.entities;

import com.churchwebsite.churchwebsite.utils.MiscUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "announcement")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String content;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt = LocalDateTime.now();

    @Column(name = "is_archived", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean archived;

    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT true")
    private boolean active = true;

    @Column(name = "is_featured", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean featured;

    @Transient
    private String excerpt;

    public String getExcerpt() {
        return MiscUtils.generateExcerpt(content, 200);
    }
}

