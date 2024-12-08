package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "membership")
@EntityListeners(AuditingEntityListener.class)
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int membershipId;

    @CreatedDate
    private LocalDateTime membershipDate;

    @Column(unique = true)
    @Email
    private String contactEmail;

    private String contactPhone;

    @Email
    @Column(unique = true)
    private String spouseEmail;

    private String spousePhone;

    private String spiritualFatherName;
    private String spiritualName;
    private String spouseSpiritualName;

    @Column(name = "is_active")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "membership_category_id")
    private MembershipCategory membershipCategory;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "spouse_user_id")
    private User spouseUser;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public Membership() {
    }

    public Membership(String contactEmail, String contactPhone, String spouseEmail, String spousePhone, String spiritualFatherName, String spiritualName, String spouseSpiritualName, boolean active, MembershipCategory membershipCategory) {
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.spouseEmail = spouseEmail;
        this.spousePhone = spousePhone;
        this.spiritualFatherName = spiritualFatherName;
        this.spiritualName = spiritualName;
        this.spouseSpiritualName = spouseSpiritualName;
        this.active = active;
        this.membershipCategory = membershipCategory;
    }

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public LocalDateTime getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(LocalDateTime membershipDate) {
        this.membershipDate = membershipDate;
    }

    public @Email String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(@Email String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public @Email String getSpouseEmail() {
        return spouseEmail;
    }

    public void setSpouseEmail(@Email String spouseEmail) {
        this.spouseEmail = spouseEmail;
    }

    public String getSpousePhone() {
        return spousePhone;
    }

    public void setSpousePhone(String spousePhone) {
        this.spousePhone = spousePhone;
    }

    public String getSpiritualFatherName() {
        return spiritualFatherName;
    }

    public void setSpiritualFatherName(String spiritualFatherName) {
        this.spiritualFatherName = spiritualFatherName;
    }

    public String getSpiritualName() {
        return spiritualName;
    }

    public void setSpiritualName(String spiritualName) {
        this.spiritualName = spiritualName;
    }

    public String getSpouseSpiritualName() {
        return spouseSpiritualName;
    }

    public void setSpouseSpiritualName(String spouseSpiritualName) {
        this.spouseSpiritualName = spouseSpiritualName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public MembershipCategory getMembershipCategory() {
        return membershipCategory;
    }

    public void setMembershipCategory(MembershipCategory membershipCategory) {
        this.membershipCategory = membershipCategory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getSpouseUser() {
        return spouseUser;
    }

    public void setSpouseUser(User spouseUser) {
        this.spouseUser = spouseUser;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Membership{" +
                "membershipCategory=" + membershipCategory +
                ", active=" + active +
                ", spouseSpiritualName='" + spouseSpiritualName + '\'' +
                ", spiritualName='" + spiritualName + '\'' +
                ", spiritualFatherName='" + spiritualFatherName + '\'' +
                ", spousePhone='" + spousePhone + '\'' +
                ", spouseEmail='" + spouseEmail + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", membershipDate=" + membershipDate +
                ", membershipId=" + membershipId +
                '}';
    }
}
