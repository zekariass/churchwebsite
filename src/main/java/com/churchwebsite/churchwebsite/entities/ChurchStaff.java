package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "church_staff")
public class ChurchStaff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int churchStaffId;

    private String churchStaffTitle;
    private String churchStaffDescription;

    @ManyToOne
    @JoinColumn(name = "church_id")
    private Church church;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    public ChurchStaff() {
    }

    public ChurchStaff(String churchStaffTitle, String churchStaffDescription, Church church, User user) {
        this.churchStaffTitle = churchStaffTitle;
        this.churchStaffDescription = churchStaffDescription;
        this.church = church;
        this.user = user;
    }

    public int getChurchStaffId() {
        return churchStaffId;
    }

    public void setChurchStaffId(int churchStaffId) {
        this.churchStaffId = churchStaffId;
    }

    public String getChurchStaffTitle() {
        return churchStaffTitle;
    }

    public void setChurchStaffTitle(String churchStaffTitle) {
        this.churchStaffTitle = churchStaffTitle;
    }

    public String getChurchStaffDescription() {
        return churchStaffDescription;
    }

    public void setChurchStaffDescription(String churchStaffDescription) {
        this.churchStaffDescription = churchStaffDescription;
    }

    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ChurchStaff{" +
                "churchStaffId=" + churchStaffId +
                ", churchStaffTitle='" + churchStaffTitle + '\'' +
                ", churchStaffDescription='" + churchStaffDescription + '\'' +
                ", church=" + church +
                ", user=" + user.getUsername() +
                '}';
    }
}
