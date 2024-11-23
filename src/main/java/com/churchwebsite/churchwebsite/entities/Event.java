package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int eventId;

    private String eventTitle;
    private String eventDescription;
    private LocalDate eventDate;
    private LocalTime eventTime;

    @Column(name = "is_featured")
    private boolean featured;

    @Column(name="is_active")
    private boolean active;

    @Column(name = "is_archived")
    private boolean archived;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_location_id")
    private Address address;

    public Event() {}

    public Event(int eventId, String eventTitle, String eventDescription, LocalDate eventDate, LocalTime eventTime, boolean featured, boolean active, boolean archived) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.featured = featured;
        this.active = active;
        this.archived = archived;
    }

    public Event(int eventId, String eventTitle, String eventDescription, LocalDate eventDate, LocalTime eventTime, boolean featured, boolean active, boolean archived, Address eventLocation) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.featured = featured;
        this.active = active;
        this.archived = archived;
        this.address = eventLocation;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", eventTitle='" + eventTitle + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventDate=" + eventDate +
                ", eventTime=" + eventTime +
                ", featured=" + featured +
                ", active=" + active +
                ", archived=" + archived +
                ", eventLocation=" + address +
                '}';
    }
}
