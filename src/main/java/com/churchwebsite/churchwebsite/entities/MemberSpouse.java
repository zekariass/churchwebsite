package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
@Table(name = "member_spouse")
public class MemberSpouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int spouseId;

    private String firstName;
    private String lastName;
    private String baptismalName;

    @Email
    private String email;
    private String phoneNumber;

    public MemberSpouse() {}

    public MemberSpouse(String firstName, String lastName, String baptismalName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.baptismalName = baptismalName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public int getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(int spouseId) {
        this.spouseId = spouseId;
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

    public String getBaptismalName() {
        return baptismalName;
    }

    public void setBaptismalName(String baptismalName) {
        this.baptismalName = baptismalName;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "MemberSpouse{" +
                "spouseId=" + spouseId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", baptismalName='" + baptismalName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
