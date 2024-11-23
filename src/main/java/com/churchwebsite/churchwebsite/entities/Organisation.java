package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "organisation")
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organisation_id")
    private int organisationId;

    private String organisationName;
    private String organisationDescription;
    private String organisationLogo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "whatsapp")
    private String whatsApp;
    private String telegram;
    private String youtube;
    private String facebook;
    private String tiktok;

    @Column(name = "xaccount")
    private String xAccount;

    @Column(name = "googleplus")
    private String googlePlus;

    @Column(name = "pinterest")
    private String pInterest;

    @Column(name = "linkedin")
    private String linkedIn;
    private String reddit;
    private String email;

    @OneToMany(mappedBy = "organisation", cascade = CascadeType.ALL)
    private List<OrganisationBanner> banners;

    public Organisation() {
    }

    public Organisation(String organisationName, String organisationDescription, String organisationLogo, Address address) {
        this.organisationName = organisationName;
        this.organisationDescription = organisationDescription;
        this.organisationLogo = organisationLogo;
        this.address = address;
    }

    public Organisation(String organisationName, String organisationDescription, String organisationLogo, Address address, String whatsApp, String telegram, String youtube, String facebook, String tiktok, String xAccount, String googlePlus, String pInterest, String linkedIn, String reddit, String email) {
        this.organisationName = organisationName;
        this.organisationDescription = organisationDescription;
        this.organisationLogo = organisationLogo;
        this.address = address;
        this.whatsApp = whatsApp;
        this.telegram = telegram;
        this.youtube = youtube;
        this.facebook = facebook;
        this.tiktok = tiktok;
        this.xAccount = xAccount;
        this.googlePlus = googlePlus;
        this.pInterest = pInterest;
        this.linkedIn = linkedIn;
        this.reddit = reddit;
        this.email = email;
    }

    public int getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(int organisationId) {
        this.organisationId = organisationId;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }

    public String getOrganisationDescription() {
        return organisationDescription;
    }

    public void setOrganisationDescription(String organisationDescription) {
        this.organisationDescription = organisationDescription;
    }

    public String getOrganisationLogo() {
        return organisationLogo;
    }

    public void setOrganisationLogo(String organisationLogo) {
        this.organisationLogo = organisationLogo;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<OrganisationBanner> getBanners() {
        return banners;
    }

    public void setBanners(List<OrganisationBanner> banners) {
        this.banners = banners;
    }

    public String getWhatsApp() {
        return whatsApp;
    }

    public void setWhatsApp(String whatsApp) {
        this.whatsApp = whatsApp;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTiktok() {
        return tiktok;
    }

    public void setTiktok(String tiktok) {
        this.tiktok = tiktok;
    }

    public String getxAccount() {
        return xAccount;
    }

    public void setxAccount(String xAccount) {
        this.xAccount = xAccount;
    }

    public String getGooglePlus() {
        return googlePlus;
    }

    public void setGooglePlus(String googlePlus) {
        this.googlePlus = googlePlus;
    }

    public String getpInterest() {
        return pInterest;
    }

    public void setpInterest(String pInterest) {
        this.pInterest = pInterest;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getReddit() {
        return reddit;
    }

    public void setReddit(String reddit) {
        this.reddit = reddit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Organisation{" +
                "organisationId=" + organisationId +
                ", organisationName='" + organisationName + '\'' +
                ", organisationDescription='" + organisationDescription + '\'' +
                ", organisationLogo='" + organisationLogo + '\'' +
                ", address=" + address +
                ", whatsApp='" + whatsApp + '\'' +
                ", telegram='" + telegram + '\'' +
                ", youtube='" + youtube + '\'' +
                ", facebook='" + facebook + '\'' +
                ", tiktok='" + tiktok + '\'' +
                ", xAccount='" + xAccount + '\'' +
                ", googlePlus='" + googlePlus + '\'' +
                ", pInterest='" + pInterest + '\'' +
                ", linkedIn='" + linkedIn + '\'' +
                ", reddit='" + reddit + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
