package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "membership_category")
public class MembershipCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int membershipCategoryId;

    @Column(unique = true)
    private String membershipName;
    private String membershipDescription;
    private double membershipAmount;

    @OneToMany(mappedBy = "membershipCategory")
    private List<Membership> members;

    public MembershipCategory() {}

    public MembershipCategory(String membershipName, String membershipDescription, double membershipAmount) {
        this.membershipName = membershipName;
        this.membershipDescription = membershipDescription;
        this.membershipAmount = membershipAmount;
    }

    public int getMembershipCategoryId() {
        return membershipCategoryId;
    }

    public void setMembershipCategoryId(int membershipCategoryId) {
        this.membershipCategoryId = membershipCategoryId;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public String getMembershipDescription() {
        return membershipDescription;
    }

    public void setMembershipDescription(String membershipDescription) {
        this.membershipDescription = membershipDescription;
    }

    public double getMembershipAmount() {
        return membershipAmount;
    }

    public void setMembershipAmount(double membershipAmount) {
        this.membershipAmount = membershipAmount;
    }

    public List<Membership> getMembers() {
        return members;
    }

    public void setMembers(List<Membership> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "MembershipCategory{" +
                "membershipName='" + membershipName + '\'' +
                ", membershipDescription='" + membershipDescription + '\'' +
                ", membershipAmount=" + membershipAmount +
                ", membershipCategoryId=" + membershipCategoryId +
                '}';
    }
}
