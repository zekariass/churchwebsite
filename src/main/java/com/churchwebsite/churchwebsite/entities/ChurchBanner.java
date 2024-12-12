package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "church_banner")
public class ChurchBanner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int churchBannerId;

    private String churchBanner;

    @ManyToOne
    @JoinColumn(name = "church_id")
    private Church church;

    public ChurchBanner() {
    }

    public ChurchBanner(String churchBanner, Church church) {
        this.churchBanner = churchBanner;
        this.church = church;
    }

    public int getChurchBannerId() {
        return churchBannerId;
    }

    public void setChurchBannerId(int churchBannerId) {
        this.churchBannerId = churchBannerId;
    }

    public void setChurchBanner(String churchBanner) {
        this.churchBanner = churchBanner;
    }

    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }

    public String getChurchBanner() {
//        String orgBanner = new String(churchBanner);
//        return orgBanner.replace("\\", "/");
        return churchBanner;
    }

    @Override
    public String toString() {
        return "ChurchBanner{" +
                "churchBannerId=" + churchBannerId +
                ", churchBanner='" + churchBanner + '\'' +
                ", church=" + church +
                '}';
    }
}
