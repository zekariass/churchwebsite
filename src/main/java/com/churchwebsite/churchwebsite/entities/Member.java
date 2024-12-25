package com.churchwebsite.churchwebsite.entities;

import com.churchwebsite.churchwebsite.enums.Gender;
import com.churchwebsite.churchwebsite.enums.MembershipPaymentMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    private String firstName;
    private String lastName;
    private String baptismalName;
    private String fatherConfessorName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "spouse_id")
    private MemberSpouse spouse;

    @Email
    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "is_active")
    private boolean active = true;

    @CreatedDate
    @Column(updatable = false)
    private LocalDate membershipDate;

    private double membershipAmount;

    @Enumerated(EnumType.STRING)
    private MembershipPaymentMethod paymentMethod;

    private String directDebitSortCode;
    private String directDebitAccount;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<MemberDependent> memberDependents;


    public Member() {
    }

    public Member(String firstName, String lastName, String baptismalName, String fatherConfessorName, MemberSpouse spouse, String email, String phoneNumber, Gender gender, boolean active, LocalDate membershipDate, double membershipAmount, MembershipPaymentMethod paymentMethod, String directDebitSortCode, String directDebitAccount, User user, Address address, List<MemberDependent> memberDependents) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.baptismalName = baptismalName;
        this.fatherConfessorName = fatherConfessorName;
        this.spouse = spouse;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.active = active;
        this.membershipDate = membershipDate;
        this.membershipAmount = membershipAmount;
        this.paymentMethod = paymentMethod;
        this.directDebitSortCode = directDebitSortCode;
        this.directDebitAccount = directDebitAccount;
        this.user = user;
        this.address = address;
        this.memberDependents = memberDependents;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBaptismalName() {
        return baptismalName;
    }

    public void setBaptismalName(String baptismalName) {
        this.baptismalName = baptismalName;
    }

    public String getFatherConfessorName() {
        return fatherConfessorName;
    }

    public void setFatherConfessorName(String fatherConfessorName) {
        this.fatherConfessorName = fatherConfessorName;
    }

    public MemberSpouse getSpouse() {
        return spouse;
    }

    public void setSpouse(MemberSpouse spouse) {
        this.spouse = spouse;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(LocalDate membershipDate) {
        this.membershipDate = membershipDate;
    }

    public double getMembershipAmount() {
        return membershipAmount;
    }

    public void setMembershipAmount(double membershipAmount) {
        this.membershipAmount = membershipAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<MemberDependent> getMemberDependents() {
        return memberDependents;
    }

    public void setMemberDependents(List<MemberDependent> memberDependents) {
        this.memberDependents = memberDependents;
    }

    public MembershipPaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(MembershipPaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDirectDebitSortCode() {
        return directDebitSortCode;
    }

    public void setDirectDebitSortCode(String directDebitSortCode) {
        this.directDebitSortCode = directDebitSortCode;
    }

    public String getDirectDebitAccount() {
        return directDebitAccount;
    }

    public void setDirectDebitAccount(String directDebitAccount) {
        this.directDebitAccount = directDebitAccount;
    }

    public List<MemberDependent> getNonNullDependents(){
        List<MemberDependent> filteredDependents = new ArrayList<>();
        for(MemberDependent dependent: this.memberDependents){
            if (!dependent.getFirstName().isEmpty() && !dependent.getLastName().isEmpty() && !dependent.getBaptismalName().isEmpty()){
                filteredDependents.add(dependent);
            }

        }

        return filteredDependents;
    }



    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", baptismalName='" + baptismalName + '\'' +
                ", fatherConfessorName='" + fatherConfessorName + '\'' +
                ", spouse=" + spouse +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", active=" + active +
                ", membershipDate=" + membershipDate +
                ", membershipAmount=" + membershipAmount +
                ", address=" + address +
                ", address=" + gender +
                ", address=" + memberDependents +
                ", address=" + paymentMethod +
                ", address=" + directDebitSortCode +
                ", address=" + directDebitAccount +
                '}';
    }
}
