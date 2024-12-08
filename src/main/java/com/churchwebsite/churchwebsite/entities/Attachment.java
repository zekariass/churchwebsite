package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "attachment")
@EntityListeners(AuditingEntityListener.class)
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int attachmentId;

    private String attachmentName;
    private String attachmentPath;

    @CreatedDate
    private LocalDateTime attachmentTime;

    @ManyToOne
    @JoinColumn(name = "attachment_type_id")
    private AttachmentType attachmentType;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    @CreatedBy
    private User uploadedBy;

    @Column(name = "is_archived")
    private boolean archived;

    public Attachment() {}

    public Attachment(String attachmentName, String attachmentPath, AttachmentType attachmentType, boolean archived) {
        this.attachmentName = attachmentName;
        this.attachmentPath = attachmentPath;
        this.attachmentType = attachmentType;
        this.archived = archived;
    }

    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
    }

    public String getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(String attachmentPath) {
        this.attachmentPath = attachmentPath;
    }

    public LocalDateTime getAttachmentTime() {
        return attachmentTime;
    }

    public void setAttachmentTime(LocalDateTime attachmentTime) {
        this.attachmentTime = attachmentTime;
    }

    public AttachmentType getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(AttachmentType attachmentType) {
        this.attachmentType = attachmentType;
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
        return "Attachment{" +
                "attachmentId=" + attachmentId +
                ", attachmentName='" + attachmentName + '\'' +
                ", attachmentPath='" + attachmentPath + '\'' +
                ", attachmentTime=" + attachmentTime +
                ", attachmentType=" + attachmentType +
                ", archived=" + archived +
                '}';
    }
}
