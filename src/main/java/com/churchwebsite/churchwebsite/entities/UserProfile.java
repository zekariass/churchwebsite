package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userProfileId;

    private String firstName;
    private String lastName;
    private String spiritualFatherName;
    private String christianName;
    private String gender;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;
    private String phoneNumber;
    private String profilePhoto;
    private String description;

    @OneToOne(mappedBy = "userProfile")
    private User user;

    @Transient
    private String fullName;

    public UserProfile() {
    }

    public UserProfile(int userProfileId, String firstName, String lastName, String spiritualFatherName, String christianName, String gender, LocalDate dob, String phoneNumber, String profilePhoto, String description) {
        this.userProfileId = userProfileId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.spiritualFatherName = spiritualFatherName;
        this.christianName = christianName;
        this.gender = gender;
        this.dob = dob;
        this.phoneNumber = phoneNumber;
        this.profilePhoto = profilePhoto;
        this.description = description;
    }

    public int getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(int userProfileId) {
        this.userProfileId = userProfileId;
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

    public String getSpiritualFatherName() {
        return spiritualFatherName;
    }

    public void setSpiritualFatherName(String spiritualFatherName) {
        this.spiritualFatherName = spiritualFatherName;
    }

    public String getChristianName() {
        return christianName;
    }

    public void setChristianName(String christianName) {
        this.christianName = christianName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "userProfileId=" + userProfileId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", spiritualFatherName='" + spiritualFatherName + '\'' +
                ", christianName='" + christianName + '\'' +
                ", gender='" + gender + '\'' +
                ", dob=" + dob +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


}
