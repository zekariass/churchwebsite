package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;

    private String street;
    private String buildingNo;
    private String houseNo;
    private String city;
    private String state;
    private String country;
    private String postCode;
    private String mapIframe;

    public Address() {
    }

    public Address(String street, String buildingNo, String houseNo, String city, String state, String country, String postCode) {
        this.street = street;
        this.buildingNo = buildingNo;
        this.houseNo = houseNo;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postCode = postCode;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }


    public String getFullAddress() {
        StringBuilder address = new StringBuilder();

        if (street != null && !street.isEmpty()) {
            address.append(street);
        }
        if (buildingNo != null && !buildingNo.isEmpty()) {
            address.append(", ").append(buildingNo);
        }
        if (houseNo != null && !houseNo.isEmpty()) {
            address.append(", ").append(houseNo);
        }
        if (city != null && !city.isEmpty()) {
            address.append(", ").append(city);
        }
        if (state != null && !state.isEmpty()) {
            address.append(", ").append(state);
        }
        if (country != null && !country.isEmpty()) {
            address.append(", ").append(country);
        }
        if (postCode != null && !postCode.isEmpty()) {
            address.append(", ").append(postCode);
        }

        // Trim leading/trailing commas or spaces
        return address.toString().replaceAll("^,\\s*", "").replaceAll("\\s*,\\s*$", "");
    }

    public String getMapIframe() {
        return mapIframe;
    }

    public void setMapIframe(String mapIframe) {
        this.mapIframe = mapIframe;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", street='" + street + '\'' +
                ", buildingNo='" + buildingNo + '\'' +
                ", houseNo='" + houseNo + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", postCode='" + postCode + '\'' +
                '}';
    }
}
