package com.churchwebsite.churchwebsite.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "membership_amount")
public class MembershipAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int membershipAmountId;

    @NotNull(message = "You must provide an amount.")
    private Double membershipAmount;

    private String membershipAmountDescription;

    @Transient
    @OneToMany(mappedBy = "membershipAmount")
    private List<Member> members;

    public MembershipAmount() {
    }

    public MembershipAmount(double membershipAmount, String membershipAmountDescription) {
        this.membershipAmount = membershipAmount;
        this.membershipAmountDescription = membershipAmountDescription;
    }

    public int getMembershipAmountId() {
        return membershipAmountId;
    }

    public void setMembershipAmountId(int membershipAmountId) {
        this.membershipAmountId = membershipAmountId;
    }

    public Double getMembershipAmount() {
        return membershipAmount;
    }

    public void setMembershipAmount(Double membershipAmount) {
        this.membershipAmount = membershipAmount;
    }

    public String getMembershipAmountDescription() {
        return membershipAmountDescription;
    }

    public void setMembershipAmountDescription(String membershipAmountDescription) {
        this.membershipAmountDescription = membershipAmountDescription;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "MembershipAmount{" +
                "membershipAmountId=" + membershipAmountId +
                ", membershipAmount=" + membershipAmount +
                ", membershipAmountDescription='" + membershipAmountDescription + '\'' +
                '}';
    }
}
