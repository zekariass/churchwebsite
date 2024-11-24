package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "media_type")
public class AttachmentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attachmentTypeId;

    private String attachmentTypeName;
    private String attachmentTypeDescription;

    public AttachmentType() {}

    public AttachmentType(String attachmentTypeName, String attachmentTypeDescription) {
        this.attachmentTypeName = attachmentTypeName;
        this.attachmentTypeDescription = attachmentTypeDescription;
    }

    public int getAttachmentTypeId() {
        return attachmentTypeId;
    }

    public void setAttachmentTypeId(int attachmentTypeId) {
        this.attachmentTypeId = attachmentTypeId;
    }

    public String getAttachmentTypeName() {
        return attachmentTypeName;
    }

    public void setAttachmentTypeName(String attachmentTypeName) {
        this.attachmentTypeName = attachmentTypeName;
    }

    public String getAttachmentTypeDescription() {
        return attachmentTypeDescription;
    }

    public void setAttachmentTypeDescription(String attachmentTypeDescription) {
        this.attachmentTypeDescription = attachmentTypeDescription;
    }

    @Override
    public String toString() {
        return "AttachmentType{" +
                "attachmentTypeId=" + attachmentTypeId +
                ", attachmentTypeName='" + attachmentTypeName + '\'' +
                ", attachmentTypeDescription='" + attachmentTypeDescription + '\'' +
                '}';
    }
}
