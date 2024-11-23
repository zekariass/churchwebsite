package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "media_type")
public class MediaType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mediaTypeId;

    private String mediaTypeName;
    private String mediaTypeDescription;

    public MediaType() {}

    public MediaType(String mediaTypeName, String mediaTypeDescription) {
        this.mediaTypeName = mediaTypeName;
        this.mediaTypeDescription = mediaTypeDescription;
    }

    public int getMediaTypeId() {
        return mediaTypeId;
    }

    public void setMediaTypeId(int mediaTypeId) {
        this.mediaTypeId = mediaTypeId;
    }

    public String getMediaTypeName() {
        return mediaTypeName;
    }

    public void setMediaTypeName(String mediaTypeName) {
        this.mediaTypeName = mediaTypeName;
    }

    public String getMediaTypeDescription() {
        return mediaTypeDescription;
    }

    public void setMediaTypeDescription(String mediaTypeDescription) {
        this.mediaTypeDescription = mediaTypeDescription;
    }

    @Override
    public String toString() {
        return "MediaType{" +
                "mediaTypeId=" + mediaTypeId +
                ", mediaTypeName='" + mediaTypeName + '\'' +
                ", mediaTypeDescription='" + mediaTypeDescription + '\'' +
                '}';
    }
}
