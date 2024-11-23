package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "blog_comment")
@EntityListeners(AuditingEntityListener.class)
public class BlogComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    private String comment;

    @LastModifiedDate
    private LocalDateTime commentTime;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "blogComment", cascade = CascadeType.ALL)
    private List<BlogCommentReply> replies;

    public BlogComment() {}

    public BlogComment(String comment, LocalDateTime commentTime, Blog blog, User user) {
        this.comment = comment;
        this.commentTime = commentTime;
        this.blog = blog;
        this.user = user;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(LocalDateTime commentTime) {
        this.commentTime = commentTime;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BlogCommentReply> getReplies() {
        return replies;
    }

    public void setReplies(List<BlogCommentReply> replies) {
        this.replies = replies;
    }

    @Override
    public String toString() {
        return "BlogComment{" +
                "commentId=" + commentId +
                ", comment='" + comment + '\'' +
                ", commentTime=" + commentTime +
                ", blog=" + blog.getBlogTitle() +
                ", user=" + user.getUsername() +
                '}';
    }
}
