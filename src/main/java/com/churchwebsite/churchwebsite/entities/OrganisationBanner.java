package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "organisation_banner")
public class OrganisationBanner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int organisationBannerId;

    private String organisationBanner;

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    public OrganisationBanner() {
    }

    public OrganisationBanner(String organisationBanner, Organisation organisation) {
        this.organisationBanner = organisationBanner;
        this.organisation = organisation;
    }

    public int getOrganisationBannerId() {
        return organisationBannerId;
    }

    public void setOrganisationBannerId(int organisationBannerId) {
        this.organisationBannerId = organisationBannerId;
    }

    public String getOrganisationBanner() {
        return organisationBanner;
    }

    public void setOrganisationBanner(String organisationBanner) {
        this.organisationBanner = organisationBanner;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    @Override
    public String toString() {
        return "OrganisationBanner{" +
                "organisationBannerId=" + organisationBannerId +
                ", organisationBanner='" + organisationBanner + '\'' +
                ", organisation=" + organisation.getOrganisationId() +
                '}';
    }
}
