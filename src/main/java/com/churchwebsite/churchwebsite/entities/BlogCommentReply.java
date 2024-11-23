package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "blog_comment_reply")
@EntityListeners(AuditingEntityListener.class)
public class BlogCommentReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int replyId;

    @ManyToOne
    @JoinColumn(name = "blog_comment_id")
    private BlogComment blogComment;

    @ManyToOne
    @JoinColumn(name = "comment_reply_id")
    private BlogCommentReply commentReply;

    @OneToMany(mappedBy = "commentReply", cascade = CascadeType.ALL)
    private List<BlogCommentReply> replyReplies;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String replyText;

    @CreatedDate
    private LocalDateTime replyTime;

    public BlogCommentReply() {}

    public BlogCommentReply(BlogComment replyToComment, BlogCommentReply replyToReply, User user, String replyText, LocalDateTime replyTime) {
        this.blogComment = replyToComment;
        this.commentReply = replyToReply;
        this.user = user;
        this.replyText = replyText;
        this.replyTime = replyTime;
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public BlogComment getBlogComment() {
        return blogComment;
    }

    public void setBlogComment(BlogComment blogComment) {
        this.blogComment = blogComment;
    }

    public BlogCommentReply getCommentReply() {
        return commentReply;
    }

    public void setCommentReply(BlogCommentReply commentReply) {
        this.commentReply = commentReply;
    }

    public List<BlogCommentReply> getReplyReplies() {
        return replyReplies;
    }

    public void setReplyReplies(List<BlogCommentReply> replyReplies) {
        this.replyReplies = replyReplies;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public LocalDateTime getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(LocalDateTime replyTime) {
        this.replyTime = replyTime;
    }

    @Override
    public String toString() {
        return "BlogCommentReply{" +
                "replyId=" + replyId +
                ", user=" + user.getUsername() +
                ", replyText='" + replyText + '\'' +
                ", replyTime=" + replyTime +
                '}';
    }
}
