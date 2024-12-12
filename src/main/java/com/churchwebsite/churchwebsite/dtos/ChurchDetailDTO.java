package com.churchwebsite.churchwebsite.dtos;
import com.churchwebsite.churchwebsite.entities.Address;
import com.churchwebsite.churchwebsite.entities.Church;
import com.churchwebsite.churchwebsite.entities.ChurchBanner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChurchDetailDTO {

    private Church church;
    private List<ChurchBanner> banners;
    private Address address;

    public ChurchDetailDTO(){}

    public ChurchDetailDTO(Church church, Address address) {
        this.church = church;
        this.address = address;
    }

    public ChurchDetailDTO(Church church, List<ChurchBanner> banners, Address address) {
        this.church = church;
        this.banners = banners;
        this.address = address;
    }

    public Church getChurch() {
        return church;
    }

    public void setChurch(Church church) {
        this.church = church;
    }

    public List<ChurchBanner> getBanners() {
        return banners;
    }

    public void setBanners(List<ChurchBanner> banners) {
        this.banners = banners;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void addBanner(ChurchBanner banner){
    if(this.banners == null){
        this.banners = new ArrayList<>();
    }
    this.banners.add(banner);
}

    @Override
    public String toString() {
        return "ChurchDetailDTO{" +
                "church=" + church +
                ", address=" + address +
                '}';
    }
}
