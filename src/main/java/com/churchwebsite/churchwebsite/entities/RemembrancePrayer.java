package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "remembrance_prayer")
@EntityListeners(AuditingEntityListener.class)
public class RemembrancePrayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;

    private String requestorFullname;
    private String christianNameOfThePrayerIsFor;
    private String spiritualFatherFullname;
    private LocalDate prayerForDate;

    @LastModifiedDate
    private LocalDate requestDate;

    private String message;

    public RemembrancePrayer() {}

    public RemembrancePrayer(String requestorFullname, String christianNameOfThePrayerIsFor, String spiritualFatherFullname, LocalDate prayerForDate, LocalDate requestDate, String message) {
        this.requestorFullname = requestorFullname;
        this.christianNameOfThePrayerIsFor = christianNameOfThePrayerIsFor;
        this.spiritualFatherFullname = spiritualFatherFullname;
        this.prayerForDate = prayerForDate;
        this.requestDate = requestDate;
        this.message = message;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getRequestorFullname() {
        return requestorFullname;
    }

    public void setRequestorFullname(String requestorFullname) {
        this.requestorFullname = requestorFullname;
    }

    public String getChristianNameOfThePrayerIsFor() {
        return christianNameOfThePrayerIsFor;
    }

    public void setChristianNameOfThePrayerIsFor(String christianNameOfThePrayerIsFor) {
        this.christianNameOfThePrayerIsFor = christianNameOfThePrayerIsFor;
    }

    public String getSpiritualFatherFullname() {
        return spiritualFatherFullname;
    }

    public void setSpiritualFatherFullname(String spiritualFatherFullname) {
        this.spiritualFatherFullname = spiritualFatherFullname;
    }

    public LocalDate getPrayerForDate() {
        return prayerForDate;
    }

    public void setPrayerForDate(LocalDate prayerForDate) {
        this.prayerForDate = prayerForDate;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RemembrancePrayer{" +
                "requestId=" + requestId +
                ", requestorFullname='" + requestorFullname + '\'' +
                ", christianNameOfThePrayerIsFor='" + christianNameOfThePrayerIsFor + '\'' +
                ", spiritualFatherFullname='" + spiritualFatherFullname + '\'' +
                ", prayerForDate=" + prayerForDate +
                ", requestDate=" + requestDate +
                ", message='" + message + '\'' +
                '}';
    }
}
