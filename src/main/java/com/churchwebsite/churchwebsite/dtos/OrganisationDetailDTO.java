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

//    private String organisationName;
//    private String organisationDescription;
//    private String organisationLogo;
//
//    private String whatsApp;
//    private String telegram;
//    private String youtube;
//    private String facebook;
//    private String tiktok;
//    private String xAccount;
//    private String googlePlus;
//    private String pInterest;
//    private String linkedIn;
//    private String reddit;
//    private String email;
//
//    private List<String> organisationBanners;
//    private String street;
//    private String buildingNo;
//    private String houseNo;
//    private String city;
//    private String state;
//    private String country;
//    private String postCode;
//
//    private Organisation organisation;
//    private List<OrganisationBanner> banners;
//    private Address address;
//
//    public OrganisationDetailDTO() {
//    }
//
//    public OrganisationDetailDTO(String organisationName, String organisationDescription, String organisationLogo, String whatsApp, String telegram, String youtube, String facebook, String tiktok, String xAccount, String googlePlus, String pInterest, String linkedIn, String reddit, String email, String street, String buildingNo, String houseNo, String city, String state, String country, String postCode) {
//        this.organisationName = organisationName;
//        this.organisationDescription = organisationDescription;
//        this.organisationLogo = organisationLogo;
//        this.whatsApp = whatsApp;
//        this.telegram = telegram;
//        this.youtube = youtube;
//        this.facebook = facebook;
//        this.tiktok = tiktok;
//        this.xAccount = xAccount;
//        this.googlePlus = googlePlus;
//        this.pInterest = pInterest;
//        this.linkedIn = linkedIn;
//        this.reddit = reddit;
//        this.email = email;
//        this.street = street;
//        this.buildingNo = buildingNo;
//        this.houseNo = houseNo;
//        this.city = city;
//        this.state = state;
//        this.country = country;
//        this.postCode = postCode;
//    }
//
//    public String getOrganisationName() {
//        return organisationName;
//    }
//
//    public void setOrganisationName(String organisationName) {
//        this.organisationName = organisationName;
//    }
//
//    public String getOrganisationDescription() {
//        return organisationDescription;
//    }
//
//    public void setOrganisationDescription(String organisationDescription) {
//        this.organisationDescription = organisationDescription;
//    }
//
//    public String getOrganisationLogo() {
//        return organisationLogo;
//    }
//
//    public void setOrganisationLogo(String organisationLogo) {
//        this.organisationLogo = organisationLogo;
//    }
//
//    public List<String> getOrganisationBanners() {
//        return organisationBanners;
//    }
//
//    public void setOrganisationBanners(List<String> organisationBanners) {
//        this.organisationBanners = organisationBanners;
//    }
//
//    public String getStreet() {
//        return street;
//    }
//
//    public void setStreet(String street) {
//        this.street = street;
//    }
//
//    public String getBuildingNo() {
//        return buildingNo;
//    }
//
//    public void setBuildingNo(String buildingNo) {
//        this.buildingNo = buildingNo;
//    }
//
//    public String getHouseNo() {
//        return houseNo;
//    }
//
//    public void setHouseNo(String houseNo) {
//        this.houseNo = houseNo;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getState() {
//        return state;
//    }
//
//    public void setState(String state) {
//        this.state = state;
//    }
//
//    public String getCountry() {
//        return country;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public String getPostCode() {
//        return postCode;
//    }
//
//    public void setPostCode(String postCode) {
//        this.postCode = postCode;
//    }
//
//    public String getWhatsApp() {
//        return whatsApp;
//    }
//
//    public void setWhatsApp(String whatsApp) {
//        this.whatsApp = whatsApp;
//    }
//
//    public String getTelegram() {
//        return telegram;
//    }
//
//    public void setTelegram(String telegram) {
//        this.telegram = telegram;
//    }
//
//    public String getYoutube() {
//        return youtube;
//    }
//
//    public void setYoutube(String youtube) {
//        this.youtube = youtube;
//    }
//
//    public String getFacebook() {
//        return facebook;
//    }
//
//    public void setFacebook(String facebook) {
//        this.facebook = facebook;
//    }
//
//    public String getTiktok() {
//        return tiktok;
//    }
//
//    public void setTiktok(String tiktok) {
//        this.tiktok = tiktok;
//    }
//
//    public String getxAccount() {
//        return xAccount;
//    }
//
//    public void setxAccount(String xAccount) {
//        this.xAccount = xAccount;
//    }
//
//    public String getGooglePlus() {
//        return googlePlus;
//    }
//
//    public void setGooglePlus(String googlePlus) {
//        this.googlePlus = googlePlus;
//    }
//
//    public String getpInterest() {
//        return pInterest;
//    }
//
//    public void setpInterest(String pInterest) {
//        this.pInterest = pInterest;
//    }
//
//    public String getLinkedIn() {
//        return linkedIn;
//    }
//
//    public void setLinkedIn(String linkedIn) {
//        this.linkedIn = linkedIn;
//    }
//
//    public String getReddit() {
//        return reddit;
//    }
//
//    public void setReddit(String reddit) {
//        this.reddit = reddit;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void addBanner(String banner){
//        if(organisationBanners == null){
//            organisationBanners = new ArrayList<>();
//        }
//        organisationBanners.add(banner);
//    }
//
//    public void toDTO(Organisation organisation, List<OrganisationBanner> banners, Address address){
//        this.setOrganisationName(organisation.getOrganisationName());
//        this.setOrganisationDescription(organisation.getOrganisationDescription());
//        this.setOrganisationLogo(organisation.getOrganisationLogo());
//        this.setOrganisationBanners(organisation.getBanners().stream().map(OrganisationBanner::getOrganisationBanner).collect(Collectors.toList()));
//
//        this.setWhatsApp(organisation.getWhatsApp());
//        this.setTelegram(organisation.getTelegram());
//        this.setYoutube(organisation.getYoutube());
//        this.setFacebook(organisation.getFacebook());
//        this.setTiktok(organisation.getTiktok());
//        this.setxAccount(organisation.getxAccount());
//        this.setGooglePlus(organisation.getGooglePlus());
//        this.setpInterest(organisation.getpInterest());
//        this.setLinkedIn(organisation.getLinkedIn());
//        this.setReddit(organisation.getReddit());
//        this.setEmail(organisation.getEmail());
//
//        this.setStreet(address.getStreet());
//        this.setBuildingNo(address.getBuildingNo());
//        this.setHouseNo(address.getHouseNo());
//        this.setCity(address.getCity());
//        this.setState(address.getState());
//        this.setCountry(address.getCountry());
//        this.setPostCode(address.getPostCode());
//
//    }
//
//    public Organisation getOrgnisation(){
//        if(this.organisation == null) {
//            this.organisation = new Organisation(
//                    this.getOrganisationName(),
//                    this.getOrganisationDescription(),
//                    this.getOrganisationLogo(),
//                    this.getAddress(),
//                    this.getWhatsApp(),
//                    this.getTelegram(),
//                    this.getYoutube(),
//                    this.getFacebook(),
//                    this.getTiktok(),
//                    this.getxAccount(),
//                    this.getGooglePlus(),
//                    this.getpInterest(),
//                    this.getLinkedIn(),
//                    this.getReddit(),
//                    this.getEmail());
//        }
//        return this.organisation;
//    }
//
//    public Address getAddress(){
//        if(this.address == null) {
//            this.address = new Address(this.getStreet(),
//                    this.getBuildingNo(),
//                    this.getHouseNo(),
//                    this.getCity(),
//                    this.getState(),
//                    this.getCountry(),
//                    this.getPostCode());
//        }
//        return this.address;
//    }
//
//    public List<OrganisationBanner> getBanners(){
//        this.banners = new ArrayList<>();
//        for (String banner : this.getOrganisationBanners()) {
//            this.banners.add(new OrganisationBanner(banner, this.getOrgnisation()));
//        }
//
//        return this.banners;
//    }
//
//    @Override
//    public String toString() {
//        return "OrganisationDetailDTO{" +
//                "organisationName='" + organisationName + '\'' +
//                ", organisationDescription='" + organisationDescription + '\'' +
//                ", organisationLogo='" + organisationLogo + '\'' +
//                ", organisationBanners=" + organisationBanners +
//                ", street='" + street + '\'' +
//                ", buildingNo='" + buildingNo + '\'' +
//                ", houseNo='" + houseNo + '\'' +
//                ", city='" + city + '\'' +
//                ", state='" + state + '\'' +
//                ", country='" + country + '\'' +
//                ", postCode='" + postCode + '\'' +
//                '}';
//    }


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
