package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "video")
@EntityListeners(AuditingEntityListener.class)
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int videoId;

    private String videoName;

    private String youtubeVideoId;

    @CreatedDate
    private LocalDateTime uploadTime;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    @CreatedBy
    private User uploadedBy;

    @Column(name = "is_archived")
    private boolean archived;

    public Video() {}

    public Video(String videoName, String youtubeVideoId, LocalDateTime uploadTime, User uploadedBy, boolean archived) {
        this.videoName = videoName;
        this.youtubeVideoId = youtubeVideoId;
        this.uploadTime = uploadTime;
        this.uploadedBy = uploadedBy;
        this.archived = archived;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public User getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(User uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    public void setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    @Override
    public String toString() {
        return "Video{" +
                "videoId=" + videoId +
                ", videoName='" + videoName + '\'' +
                ", videoPath='" + youtubeVideoId + '\'' +
                ", uploadTime=" + uploadTime +
                ", archived=" + archived +
                '}';
    }
}
