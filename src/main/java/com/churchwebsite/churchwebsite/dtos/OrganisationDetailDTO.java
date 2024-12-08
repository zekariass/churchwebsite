package com.churchwebsite.churchwebsite.dtos;
import com.churchwebsite.churchwebsite.entities.Address;
import com.churchwebsite.churchwebsite.entities.Organisation;
import com.churchwebsite.churchwebsite.entities.OrganisationBanner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrganisationDetailDTO {

    private Organisation organisation;
    private List<OrganisationBanner> banners;
    private Address address;

    public OrganisationDetailDTO(){}

    public OrganisationDetailDTO(Organisation organisation, Address address) {
        this.organisation = organisation;
        this.address = address;
    }

    public OrganisationDetailDTO(Organisation organisation, List<OrganisationBanner> banners, Address address) {
        this.organisation = organisation;
        this.banners = banners;
        this.address = address;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public void setOrganisation(Organisation organisation) {
        this.organisation = organisation;
    }

    public List<OrganisationBanner> getBanners() {
        return banners;
    }

    public void setBanners(List<OrganisationBanner> banners) {
        this.banners = banners;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void addBanner(OrganisationBanner banner){
    if(this.banners == null){
        this.banners = new ArrayList<>();
    }
    this.banners.add(banner);
}

    @Override
    public String toString() {
        return "OrganisationDetailDTO{" +
                "organisation=" + organisation +
                ", address=" + address +
                '}';
    }
}
