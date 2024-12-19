package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "album")
@EntityListeners(AuditingEntityListener.class)
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int albumId;

    @NotNull(message = "Provide album name.")
    private String albumName;

    private String albumDescription;

    @CreatedDate
    private LocalDateTime creationTime;

    @Column(name = "is_archived")
    private boolean archived;

    @OneToMany(mappedBy = "album")
    private List<Image> imageList;

    public Album() {}

    public Album(String albumName, String albumDescription) {
        this.albumName = albumName;
        this.albumDescription = albumDescription;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumDescription() {
        return albumDescription;
    }

    public void setAlbumDescription(String albumDescription) {
        this.albumDescription = albumDescription;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public List<Image> getMediaList() {
        return imageList;
    }

    public void setMediaList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    @Override
    public String toString() {
        return "AlbumController{" +
                "albumId=" + albumId +
                ", albumName='" + albumName + '\'' +
                ", albumDescription='" + albumDescription + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", isArchived='" + archived + '\'' +
                '}';
    }
}
