package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "organisation_staff")
public class OrganisationStaff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int organisationStaffId;

    private String organisationStaffTitle;
    private String organisationStaffDescription;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    public OrganisationStaff() {
    }

    public OrganisationStaff(String organisationStaffTitle, String organisationStaffDescription, Organisation organisation, User user) {
        this.organisationStaffTitle = organisationStaffTitle;
        this.organisationStaffDescription = organisationStaffDescription;
        this.organisation = organisation;
        this.user = user;
    }

    public int getOrganisationStaffId() {
        return organisationStaffId;
    }

    public void setOrganisationStaffId(int organisationStaffId) {
        this.organisationStaffId = organisationStaffId;
    }

    public String getOrganisationStaffTitle() {
        return organisationStaffTitle;
    }

    public void setOrganisationStaffTitle(String organisationStaffTitle) {
        this.organisationStaffTitle = organisationStaffTitle;
    }

    public String getOrganisationStaffDescription() {
        return organisationStaffDescription;
    }

    public void setOrganisationStaffDescription(String organisationStaffDescription) {
        this.organisationStaffDescription = organisationStaffDescription;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "OrganisationStaff{" +
                "organisationStaffId=" + organisationStaffId +
                ", organisationStaffTitle='" + organisationStaffTitle + '\'' +
                ", organisationStaffDescription='" + organisationStaffDescription + '\'' +
                ", organisation=" + organisation +
                ", user=" + user.getUsername() +
                '}';
    }
}
