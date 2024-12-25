package com.churchwebsite.churchwebsite.entities;

import com.churchwebsite.churchwebsite.enums.HolyMatrimonyRequestType;
import com.churchwebsite.churchwebsite.enums.ServiceStatus;
import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "holy_matrimony")
@EntityListeners(AuditingEntityListener.class)
public class HolyMatrimony {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int requestId;
    private String groomFullname;
    private String brideFullname;
    private String spiritualFatherFullname;
    private LocalDate matrimonyDate;
    private String email;
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Enumerated(EnumType.STRING)
    private HolyMatrimonyRequestType requiredService;

    @Enumerated(EnumType.STRING)
    private ServiceStatus service_status;

    @LastModifiedDate
    private LocalDate requestDate;

    private String message;

    public HolyMatrimony() {}

    public HolyMatrimony(String groomFullname, String brideFullname, String spiritualFatherFullname, LocalDate matrimonyDate, String email, String phoneNumber, Address address, HolyMatrimonyRequestType requiredService, ServiceStatus service_status, LocalDate requestDate, String message) {
        this.groomFullname = groomFullname;
        this.brideFullname = brideFullname;
        this.spiritualFatherFullname = spiritualFatherFullname;
        this.matrimonyDate = matrimonyDate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.requiredService = requiredService;
        this.service_status = service_status;
        this.requestDate = requestDate;
        this.message = message;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getGroomFullname() {
        return groomFullname;
    }

    public void setGroomFullname(String groomFullname) {
        this.groomFullname = groomFullname;
    }

    public String getBrideFullname() {
        return brideFullname;
    }

    public void setBrideFullname(String brideFullname) {
        this.brideFullname = brideFullname;
    }

    public String getSpiritualFatherFullname() {
        return spiritualFatherFullname;
    }

    public void setSpiritualFatherFullname(String spiritualFatherFullname) {
        this.spiritualFatherFullname = spiritualFatherFullname;
    }

    public LocalDate getMatrimonyDate() {
        return matrimonyDate;
    }

    public void setMatrimonyDate(LocalDate matrimonyDate) {
        this.matrimonyDate = matrimonyDate;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public HolyMatrimonyRequestType getRequiredService() {
        return requiredService;
    }

    public void setRequiredService(HolyMatrimonyRequestType requiredService) {
        this.requiredService = requiredService;
    }

    public ServiceStatus getService_status() {
        return service_status;
    }

    public void setService_status(ServiceStatus service_status) {
        this.service_status = service_status;
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
        return "HolyMatrimony{" +
                "requestId=" + requestId +
                ", groomFullname='" + groomFullname + '\'' +
                ", brideFullname='" + brideFullname + '\'' +
                ", spiritualFatherFullname='" + spiritualFatherFullname + '\'' +
                ", matrimonyDate=" + matrimonyDate +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address +
                ", requiredService=" + requiredService +
                ", service_status=" + service_status +
                ", requestDate=" + requestDate +
                ", message='" + message + '\'' +
                '}';
    }
}
