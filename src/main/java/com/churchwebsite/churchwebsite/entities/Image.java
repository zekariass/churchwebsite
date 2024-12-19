package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "image")
@EntityListeners(AuditingEntityListener.class)
public class Image implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int imageId;

    private String imageName;

    private String imagePath;

    @CreatedDate
    private LocalDateTime uploadTime;

//    private String mediaType;

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

    public Image() {
    }

    public Image(String imageName, String imagePath, LocalDateTime uploadTime, Album album, User uploadedBy, boolean archived) {
        this.imageName = imageName;
        this.imagePath = imagePath;
        this.uploadTime = uploadTime;
        this.album = album;
        this.uploadedBy = uploadedBy;
        this.archived = archived;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public LocalDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(LocalDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public @NotNull(message = "Album must be selected or created new one.") Album getAlbum() {
        return album;
    }

    public void setAlbum(@NotNull(message = "Album must be selected or created new one.") Album album) {
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
        return "Image{" +
                "imageId=" + imageId +
                ", imageName='" + imageName + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", uploadTime=" + uploadTime +
                ", album=" + album +
                ", archived=" + archived +
                '}';
    }

    @Override
    public Image clone() {
        try {
            Image clone = (Image) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
