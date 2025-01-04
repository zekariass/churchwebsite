package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int blogId;

    private String blogTitle;
    private String blogText;

    @CreatedDate
    private LocalDateTime blogTime;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "is_archived")
    private boolean archived;

    @ManyToOne
    @JoinColumn(name = "blog_category_id")
    private BlogCategory blogCategory;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;

    @Transient
    private String excerpt;


    public Blog() {}

    public Blog(String blogTitle, String blogText, LocalDateTime blogTime, boolean active, boolean archived, BlogCategory blogCategory, User userId) {
        this.blogTitle = blogTitle;
        this.blogText = blogText;
        this.blogTime = blogTime;
        this.active = active;
        this.archived = archived;
        this.blogCategory = blogCategory;
        this.userId = userId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public BlogCategory getBlogCategory() {
        return blogCategory;
    }

    public void setBlogCategory(BlogCategory blogCategory) {
        this.blogCategory = blogCategory;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getBlogTime() {
        return blogTime;
    }

    public void setBlogTime(LocalDateTime blogTime) {
        this.blogTime = blogTime;
    }

    public String getBlogText() {
        return blogText;
    }

    public void setBlogText(String blogText) {
        this.blogText = blogText;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "blogId=" + blogId +
                ", blogTitle='" + blogTitle + '\'' +
                ", blogText='" + blogText + '\'' +
                ", blogTime=" + blogTime +
                ", active=" + active +
                ", archived=" + archived +
                ", blogCategory=" + blogCategory +
                '}';
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }
}
