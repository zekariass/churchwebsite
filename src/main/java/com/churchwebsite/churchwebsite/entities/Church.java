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

    private String phoneNumber1;
    private String phoneNumber2;

    private String whatsAppChat;
    private String whatsAppGroup;
    private String telegramChat;
    private String telegramGroup;
    private String youtube;
    private String facebook;
    private String tiktok;

    private String xAccount;

    private String googlePlus;

    private String pInterest;

    private String linkedIn;
    private String reddit;
    private String email;
    private String bankInfo;

    @OneToMany(mappedBy = "church", cascade = CascadeType.ALL)
    private List<ChurchBanner> banners;

    public Church() {
    }

    public Church(String churchName, String churchDescription, String churchLogo, Address address, String phoneNumber1, String phoneNumber2, String whatsAppChat, String whatsAppGroup, String telegramChat, String telegramGroup, String youtube, String facebook, String tiktok, String xAccount, String googlePlus, String pInterest, String linkedIn, String reddit, String email) {
        this.churchName = churchName;
        this.churchDescription = churchDescription;
        this.churchLogo = churchLogo;
        this.address = address;
        this.phoneNumber1 = phoneNumber1;
        this.phoneNumber2 = phoneNumber2;
        this.whatsAppChat = whatsAppChat;
        this.whatsAppGroup = whatsAppGroup;
        this.telegramChat = telegramChat;
        this.telegramGroup = telegramGroup;
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

    public String getPhoneNumber1() {
        return phoneNumber1;
    }

    public void setPhoneNumber1(String phoneNumber1) {
        this.phoneNumber1 = phoneNumber1;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getWhatsAppChat() {
        return whatsAppChat;
    }

    public void setWhatsAppChat(String whatsAppChat) {
        this.whatsAppChat = whatsAppChat;
    }

    public String getWhatsAppGroup() {
        return whatsAppGroup;
    }

    public void setWhatsAppGroup(String whatsAppGroup) {
        this.whatsAppGroup = whatsAppGroup;
    }

    public String getTelegramChat() {
        return telegramChat;
    }

    public void setTelegramChat(String telegramChat) {
        this.telegramChat = telegramChat;
    }

    public String getTelegramGroup() {
        return telegramGroup;
    }

    public void setTelegramGroup(String telegramGroup) {
        this.telegramGroup = telegramGroup;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    @Override
    public String toString() {
        return "Church{" +
                "email='" + email + '\'' +
                ", reddit='" + reddit + '\'' +
                ", linkedIn='" + linkedIn + '\'' +
                ", pInterest='" + pInterest + '\'' +
                ", googlePlus='" + googlePlus + '\'' +
                ", xAccount='" + xAccount + '\'' +
                ", tiktok='" + tiktok + '\'' +
                ", facebook='" + facebook + '\'' +
                ", youtube='" + youtube + '\'' +
                ", telegramGroup='" + telegramGroup + '\'' +
                ", telegramChat='" + telegramChat + '\'' +
                ", whatsAppGroup='" + whatsAppGroup + '\'' +
                ", whatsAppChat='" + whatsAppChat + '\'' +
                ", phoneNumber2='" + phoneNumber2 + '\'' +
                ", phoneNumber1='" + phoneNumber1 + '\'' +
                ", address=" + address +
                ", churchLogo='" + churchLogo + '\'' +
                ", churchDescription='" + churchDescription + '\'' +
                ", churchName='" + churchName + '\'' +
                ", churchId=" + churchId +
                '}';
    }
}
