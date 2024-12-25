package com.churchwebsite.churchwebsite.entities;

import com.churchwebsite.churchwebsite.enums.Gender;
import com.churchwebsite.churchwebsite.enums.Relationship;
import jakarta.persistence.*;

@Entity
@Table(name = "member_dependent")
public class MemberDependent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dependentId;
    private String firstName;
    private String lastName;
    private String baptismalName;

    @Enumerated(EnumType.STRING)
    private Relationship relationshipToMember;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int age;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public MemberDependent() {}

    public MemberDependent(String firstName, String lastName, String baptismalName, Relationship relationshipToMember, Gender gender, int age, Member member) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.baptismalName = baptismalName;
        this.relationshipToMember = relationshipToMember;
        this.gender = gender;
        this.age = age;
        this.member = member;
    }

    public int getDependentId() {
        return dependentId;
    }

    public void setDependentId(int dependentId) {
        this.dependentId = dependentId;
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

    public Relationship getRelationshipToMember() {
        return relationshipToMember;
    }

    public void setRelationshipToMember(Relationship relationshipToMember) {
        this.relationshipToMember = relationshipToMember;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "MemberDependent{" +
                "dependentId=" + dependentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", baptismalName='" + baptismalName + '\'' +
                ", relationshipToMember='" + relationshipToMember + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                '}';
    }
}
