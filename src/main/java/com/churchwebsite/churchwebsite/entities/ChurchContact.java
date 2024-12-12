package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "church_contact")
public class ChurchContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contactId;

    private String contactFullName;
    private String contactPhone;
    private String contactEmail;
    private  String contactDescription;

    public ChurchContact() {
    }

    public ChurchContact(String contactFullName, String contactPhone, String contactEmail, String contactDescription) {
        this.contactFullName = contactFullName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.contactDescription = contactDescription;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactFullName() {
        return contactFullName;
    }

    public void setContactFullName(String contactFullName) {
        this.contactFullName = contactFullName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactDescription() {
        return contactDescription;
    }

    public void setContactDescription(String contactDescription) {
        this.contactDescription = contactDescription;
    }

    @Override
    public String toString() {
        return "ChurchContact{" +
                "contactId=" + contactId +
                ", contactFullName='" + contactFullName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactDescription='" + contactDescription + '\'' +
                '}';
    }
}
