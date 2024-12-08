package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "landing_content")
@EntityListeners(AuditingEntityListener.class)
public class LandingContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int landingContentId;

    private String landingContentTitle;
    private String content;

    @LastModifiedDate
    private LocalDateTime contentCreationTime;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "is_featured")
    private boolean featured;

    @Column(name = "is_archived")
    private boolean archived;

    public LandingContent() {}

    public LandingContent(String landingContentTitle, String content, boolean active, boolean featured, boolean archived) {
        this.landingContentTitle = landingContentTitle;
        this.content = content;
        this.active = active;
        this.featured = featured;
        this.archived = archived;
    }

    public int getLandingContentId() {
        return landingContentId;
    }

    public void setLandingContentId(int landingContentId) {
        this.landingContentId = landingContentId;
    }

    public String getLandingContentTitle() {
        return landingContentTitle;
    }

    public void setLandingContentTitle(String landingContentTitle) {
        this.landingContentTitle = landingContentTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getContentCreationTime() {
        return contentCreationTime;
    }

    public void setContentCreationTime(LocalDateTime contentCreationTime) {
        this.contentCreationTime = contentCreationTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public String toString() {
        return "LandingContent{" +
                "landingContentId=" + landingContentId +
                ", landingContentTitle='" + landingContentTitle + '\'' +
                ", content='" + content + '\'' +
                ", contentCreationTime=" + contentCreationTime +
                ", active=" + active +
                ", featured=" + featured +
                ", archived=" + archived +
                '}';
    }
}
