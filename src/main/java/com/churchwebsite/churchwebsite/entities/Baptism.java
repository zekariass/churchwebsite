package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity(name = "baptism")
@EntityListeners(AuditingEntityListener.class)
public class Baptism {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;
    private String childFatherFullname;
    private  String childMotherFullname;
    private String childFullname;
    private String childGodParentFullname;
    private LocalDate childDob;
    private String email;
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    private String requiredService;

    @Enumerated(EnumType.STRING)
    private String serviceStatus;

    @LastModifiedDate
    private LocalDate requestDate;
    private String message;

    public Baptism() {}

    public Baptism(String childFatherFullname, String childMotherFullname, String childFullname, String child_godParentFullname, LocalDate childDob, String email, String phoneNumber, Address address, String requiredService, String serviceStatus, LocalDate requestDate, String message) {
        this.childFatherFullname = childFatherFullname;
        this.childMotherFullname = childMotherFullname;
        this.childFullname = childFullname;
        this.childGodParentFullname = child_godParentFullname;
        this.childDob = childDob;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.requiredService = requiredService;
        this.serviceStatus = serviceStatus;
        this.requestDate = requestDate;
        this.message = message;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getChildFatherFullname() {
        return childFatherFullname;
    }

    public void setChildFatherFullname(String childFatherFullname) {
        this.childFatherFullname = childFatherFullname;
    }

    public String getChildMotherFullname() {
        return childMotherFullname;
    }

    public void setChildMotherFullname(String childMotherFullname) {
        this.childMotherFullname = childMotherFullname;
    }

    public String getChildFullname() {
        return childFullname;
    }

    public void setChildFullname(String childFullname) {
        this.childFullname = childFullname;
    }

    public String getChild_godParentFullname() {
        return childGodParentFullname;
    }

    public void setChild_godParentFullname(String child_godParentFullname) {
        this.childGodParentFullname = child_godParentFullname;
    }

    public LocalDate getChildDob() {
        return childDob;
    }

    public void setChildDob(LocalDate childDob) {
        this.childDob = childDob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddressId() {
        return address;
    }

    public void setAddressId(Address address) {
        this.address = address;
    }

    public String getRequiredService() {
        return requiredService;
    }

    public void setRequiredService(String requiredService) {
        this.requiredService = requiredService;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Baptism{" +
                "requestId=" + requestId +
                ", childFatherFullname='" + childFatherFullname + '\'' +
                ", childMotherFullname='" + childMotherFullname + '\'' +
                ", childFullname='" + childFullname + '\'' +
                ", child_godParentFullname='" + childGodParentFullname + '\'' +
                ", childDob=" + childDob +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", addressId=" + address +
                ", requiredService='" + requiredService + '\'' +
                ", serviceStatus='" + serviceStatus + '\'' +
                ", requestDate=" + requestDate +
                ", message='" + message + '\'' +
                '}';
    }
}

