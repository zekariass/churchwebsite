package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "contact_us")
@EntityListeners(AuditingEntityListener.class)
public class ContactUs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contactUsId;

    @NotEmpty(message = "Firstname cannot be empty.")
    private String firstName;

    @NotEmpty(message = "Lastname cannot be empty.")
    private String lastName;

    @Column(unique = true)
    @Email(message = "Invalid email.")
    @NotEmpty(message = "Email can not be empty.")
    private String email;

    @NotEmpty(message = "Phone number cannot be empty.")
    private String phoneNumber;

    @NotEmpty(message = "Message cannot be empty.")
    private String message;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime messageTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_read")
    private boolean read = false;

    private LocalDateTime readTime;

    @OneToMany(mappedBy = "contactUsMessage", cascade = CascadeType.ALL)
    private List<MessageReply> replies;

    public ContactUs() {}

    public ContactUs(String firstName, String lastName, String email, String phoneNumber, String message, LocalDateTime messageTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.messageTime = messageTime;
    }

    public int getContactUsId() {
        return contactUsId;
    }

    public void setContactUsId(int contactUsId) {
        this.contactUsId = contactUsId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(LocalDateTime messageTime) {
        this.messageTime = messageTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public LocalDateTime getReadTime() {
        return readTime;
    }

    public void setReadTime(LocalDateTime readTime) {
        this.readTime = readTime;
    }

    public boolean isRead() {
        return read;
    }

    public List<MessageReply> getReplies() {
        return replies;
    }

    public void setReplies(List<MessageReply> replies) {
        this.replies = replies;
    }

    @Override
    public String toString() {
        return "ContactUs{" +
                "contactUsId='" + contactUsId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", message='" + message + '\'' +
                ", messageTime=" + messageTime +
                '}';
    }
}
