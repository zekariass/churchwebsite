package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@EntityListeners(AuditingEntityListener.class)
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notificationId;
    private String notificationSubject;
    private String notificationMessage;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime notificationSentTime;

    public Notification() {}

    public Notification(String notificationSubject, String notificationMessage, LocalDateTime notificationSentTime) {
        this.notificationSubject = notificationSubject;
        this.notificationMessage = notificationMessage;
        this.notificationSentTime = notificationSentTime;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationSubject() {
        return notificationSubject;
    }

    public void setNotificationSubject(String notificationSubject) {
        this.notificationSubject = notificationSubject;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public LocalDateTime getNotificationSentTime() {
        return notificationSentTime;
    }

    public void setNotificationSentTime(LocalDateTime notificationSentTime) {
        this.notificationSentTime = notificationSentTime;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", notificationSubject='" + notificationSubject + '\'' +
                ", notificationMessage='" + notificationMessage + '\'' +
                ", notificationSentTime=" + notificationSentTime +
                '}';
    }
}
