package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "news")
@EntityListeners(AuditingEntityListener.class)
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int newsId;
    private String newsTitle;
    private String newsText;

    @LastModifiedDate
    private LocalDateTime newsPostTime;

    @Column(name = "is_featured")
    private boolean featured;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "is_archived")
    private boolean archived;

    @ManyToOne
    @JoinColumn(name = "posted_by")
    @CreatedBy
    private User postedBy;

    public News() {}

    public News(String newsTitle, String newsText, boolean featured, boolean active, boolean archived) {
        this.newsTitle = newsTitle;
        this.newsText = newsText;
        this.featured = featured;
        this.active = active;
        this.archived = archived;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsText() {
        return newsText;
    }

    public void setNewsText(String newsText) {
        this.newsText = newsText;
    }

    public LocalDateTime getNewsPostTime() {
        return newsPostTime;
    }

    public void setNewsPostTime(LocalDateTime newsPostTime) {
        this.newsPostTime = newsPostTime;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }

    @Override
    public String toString() {
        return "News{" +
                "newsId=" + newsId +
                ", newsTitle='" + newsTitle + '\'' +
                ", newsText='" + newsText + '\'' +
                ", newsPostTime=" + newsPostTime +
                ", featured=" + featured +
                ", active=" + active +
                ", archived=" + archived +
                '}';
    }
}
