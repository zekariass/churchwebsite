package com.churchwebsite.churchwebsite.entities;

import com.churchwebsite.churchwebsite.utils.MiscUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "message_reply")
public class MessageReply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email(message = "Provide valid email.")
    @NotEmpty(message = "Email cannot be empty.")
    @Column(name = "receiver_email", nullable = false, length = 100)
    private String receiverEmail;

    @ManyToOne
    @JoinColumn(name = "contact_us_id")
    private ContactUs contactUsMessage;

    @NotEmpty(message = "Subject cannot be empty")
    @Column(name = "subject", nullable = false, length = 255)
    private String subject;

    @NotEmpty(message = "Message cannot be empty.")
    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "is_follow_up")
    private boolean followUp;

    @CreatedDate
    @Column(name = "sent_at", nullable = false, updatable = false)
    private LocalDateTime sentAt;

    @Transient
    private String excerpt;


    public String getExcerpt() {
        return MiscUtils.generateExcerpt(message, 200);
        }
}