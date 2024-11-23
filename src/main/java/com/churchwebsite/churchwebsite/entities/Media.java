package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "media")
@EntityListeners(AuditingEntityListener.class)
public class Media implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mediaId;

    private String mediaName;

    private String mediaPath;

    @CreatedDate
    private LocalDateTime uploadTime;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE,
            CascadeType.DETACH
    })
    @JoinColumn(name = "media_type_id")
    private MediaType mediaType;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "album_id")
    @NotNull(message = "Album must be selected or created new one.")
    private Album album;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    @CreatedBy
    private User uploadedBy;

    @Column(name = "is_archived")
    private boolean archived;

    public Media() {
    }

    public Media(String mediaName, String mediaPath, MediaType mediaType, Album album, User uploadedBy) {
        this.mediaName = mediaName;
        this.mediaPath = mediaPath;
        this.mediaType = mediaType;
        this.album = album;
        this.uploadedBy = uploadedBy;
    }

    public int getMediaId() {
        return mediaId;
    }

    public void setMediaId(int mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
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

    @Override
    public String toString() {
        return "Media{" +
                "mediaName='" + mediaName + '\'' +
                ", mediaPath='" + mediaPath + '\'' +
                ", uploadTime=" + uploadTime +
                ", mediaType=" + mediaType +
                ", album=" + album +
                ", mediaId=" + mediaId +
                ", isArchived=" + archived +
                '}';
    }

    @Override
    public Media clone() {
        try {
            Media clone = (Media) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
