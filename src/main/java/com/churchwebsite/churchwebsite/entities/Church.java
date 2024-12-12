package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "church")
public class Church {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "church_id")
    private int churchId;

    private String churchName;
    private String churchDescription;
    private String churchLogo;

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

    @OneToMany(mappedBy = "church", cascade = CascadeType.ALL)
    private List<ChurchBanner> banners;

    public Church() {
    }

    public Church(String churchName, String churchDescription, String whatsApp, String telegram, String youtube, String xAccount, String facebook, String tiktok, String googlePlus, String pInterest, String linkedIn, String reddit, String email) {
        this.churchName = churchName;
        this.churchDescription = churchDescription;
        this.whatsApp = whatsApp;
        this.telegram = telegram;
        this.youtube = youtube;
        this.xAccount = xAccount;
        this.facebook = facebook;
        this.tiktok = tiktok;
        this.googlePlus = googlePlus;
        this.pInterest = pInterest;
        this.linkedIn = linkedIn;
        this.reddit = reddit;
        this.email = email;
    }

    public int getChurchId() {
        return churchId;
    }

    public void setChurchId(int churchId) {
        this.churchId = churchId;
    }

    public String getChurchName() {
        return churchName;
    }

    public void setChurchName(String churchName) {
        this.churchName = churchName;
    }

    public String getChurchDescription() {
        return churchDescription;
    }

    public void setChurchDescription(String churchDescription) {
        this.churchDescription = churchDescription;
    }

    public String getChurchLogo() {
        return churchLogo;
    }

    public void setChurchLogo(String churchLogo) {
        this.churchLogo = churchLogo;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public List<ChurchBanner> getBanners() {
        return banners;
    }

    public void setBanners(List<ChurchBanner> banners) {
        this.banners = banners;
    }

    @Override
    public String toString() {
        return "Church{" +
                "churchId=" + churchId +
                ", churchName='" + churchName + '\'' +
                ", churchDescription='" + churchDescription + '\'' +
                ", churchLogo='" + churchLogo + '\'' +
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
